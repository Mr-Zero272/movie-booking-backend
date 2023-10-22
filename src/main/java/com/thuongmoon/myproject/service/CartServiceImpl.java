package com.thuongmoon.myproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thuongmoon.myproject.dao.AuditoriumDao;
import com.thuongmoon.myproject.dao.CartDao;
import com.thuongmoon.myproject.dao.OrderDetailDao;
import com.thuongmoon.myproject.dao.OrderItemDao;
import com.thuongmoon.myproject.dao.SeatStatusDao;
import com.thuongmoon.myproject.dao.TicketDao;
import com.thuongmoon.myproject.dto.CheckoutResponse;
import com.thuongmoon.myproject.dto.SeatDto;
import com.thuongmoon.myproject.dto.TicketInCart;
import com.thuongmoon.myproject.model.Auditorium;
import com.thuongmoon.myproject.model.Cart;
import com.thuongmoon.myproject.model.Order_detail;
import com.thuongmoon.myproject.model.Order_item;
import com.thuongmoon.myproject.model.SeatStatus;
import com.thuongmoon.myproject.model.SeatStatusE;
import com.thuongmoon.myproject.model.Ticket;
import com.thuongmoon.myproject.model.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

	@Autowired
	private final CartDao cartDao;
	@Autowired
	private final TicketDao ticketDao;
	@Autowired
	private final SeatStatusDao seatStatusDao;
	@Autowired
	private final AuditoriumDao auditoriumDao;
	@Autowired
	private final OrderDetailDao orderDetailDao;
	@Autowired
	private final OrderItemDao orderItemDao;

	@Override
	public Cart getCurretCartFromUser(User user) {
		Cart cart = cartDao.findCurrentActiveCartByUser(user).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setActive(true);
			LocalDateTime lt = LocalDateTime.now();
			newCart.setLastUpdate(lt);
			newCart.setUser(user);
			newCart.setTotalTicket(0);
			cartDao.save(newCart);
			return newCart;
		});

		return cart;
	}

	@Override
	public List<TicketInCart> getListTicket(User user) {
		Cart cart = cartDao.findCurrentActiveCartByUser(user).orElseThrow();
		List<Ticket> tickets = ticketDao.findAllTicketInCurrentCart(cart);
		List<TicketInCart> ticketInCarts = new ArrayList<>();
		for (Ticket ticket : tickets) {
			ticketInCarts.add(new TicketInCart(ticket.getSeatStatus(),
					ticket.getSeatStatus().getScreening().getMovie().getTitle(),
					ticket.getSeatStatus().getScreening().getMovie().getDirector(),
					ticket.getSeatStatus().getScreening().getMovie().getVerticalImage(),
					ticket.getSeatStatus().getScreening().getMovie().getHorizontalImage(),
					ticket.getSeatStatus().getScreening().getMovie().getId()));
		}
		return ticketInCarts;
	}

	@Override
	@Transactional
	public void addTicketToCart(List<Long> seatStatusIds, Cart userCart) {
		int quantity = 0;
		for (Long id : seatStatusIds) {
			boolean isTickInCart = userCart.getTickets().stream().anyMatch(ticket -> ticket.getId().equals(id));
			if (!isTickInCart) {
				SeatStatus seatStatus = seatStatusDao.findById(id).orElseThrow();
				// seatStatus.setStatus(SeatStatusE.booked);
				Ticket newTicket = new Ticket();
				newTicket.setCart(userCart);
				newTicket.setSeatStatus(seatStatus);
				ticketDao.save(newTicket);
				// seatStatusDao.save(seatStatus);
				quantity++;
			}
		}
		userCart.setTotalTicket(userCart.getTotalTicket() + quantity);
		cartDao.save(userCart);
//		for(Ticket ticket : tickets) {
//			ticketDao.save(ticket);			
//		}
	}

	@Override
	@Transactional
	public CheckoutResponse addTicketBooked(List<Long> seatStatusIds, boolean paid, User user) {
		int totalPayment = 0;
		Order_detail orderDetail = new Order_detail();
		LocalDateTime lt = LocalDateTime.now();
		orderDetail.setCheckoutAt(lt);
		orderDetail.setTotal(0);
		orderDetail.setPaid(paid);
		orderDetail.setUser(user);
		orderDetailDao.save(orderDetail);

		int totalTicketBooked = 0;
		List<Long> ids = new ArrayList<>();
		for (Long id : seatStatusIds) {
			// delete ticket
			Optional<Ticket> ticket = ticketDao.findById(id);
			if (ticket.isPresent()) {
				ticketDao.deleteById(id);
				totalTicketBooked++;
			}

			// save new state for that seat
			SeatStatus seatStatus = seatStatusDao.findById(id).orElseThrow();
			if (seatStatus.getStatus().equals(SeatStatusE.booked)) {
				ids.add(id);
			} else {
				seatStatus.setStatus(SeatStatusE.booked);
				seatStatusDao.save(seatStatus);
				totalPayment += seatStatus.getPrice();
			}

			// add that ticket as booked
			Order_item newTicket = new Order_item();
			newTicket.setOrderDetail(orderDetail);
			newTicket.setSeatStatus(seatStatus);

			// save
			orderItemDao.save(newTicket);
		}
		// delete cart if there was no ticket in that cart active = false
		Cart userCart = cartDao.findCurrentActiveCartByUser(user).orElseThrow();
		List<Ticket> tickets = ticketDao.findAllTicketInCurrentCart(userCart);
		if (tickets.isEmpty()) {
			userCart.setActive(false);
			cartDao.save(userCart);
		} else {
			userCart.setTotalTicket(userCart.getTotalTicket() - totalTicketBooked);
			cartDao.save(userCart);
		}
		orderDetail.setTotal(totalPayment);
		orderDetailDao.save(orderDetail);

		String message = "";
		if (ids.size() > 0) {
			message = "There is(are) some tiket(s) are booked!";
		} else {
			message = "success";
		}
		CheckoutResponse checkoutResponse = new CheckoutResponse();
		checkoutResponse.setIds(ids);
		checkoutResponse.setMessage(message);
		return checkoutResponse;
	}

	@Override
	public List<SeatDto> getSeats(Long screeningId, Long auditoriumId) {
		Auditorium auditorium = auditoriumDao.findById(auditoriumId).orElseThrow();
		// System.out.print( screeningId + " fadfasdfsdf\n\n ");
		List<String[]> seatArrList = seatStatusDao.findSeatListByScreeningStartAndAuditorium(screeningId, auditorium);
		List<SeatDto> seatDtos = new ArrayList<>();
		for (int i = 0; i < seatArrList.size(); i++) {
			seatDtos.add(new SeatDto(Long.parseLong(seatArrList.get(i)[0]), seatArrList.get(i)[1],
					seatArrList.get(i)[2], seatArrList.get(i)[3], Integer.parseInt(seatArrList.get(i)[4])));
		}
		return seatDtos;
	}

	@Override
	public List<TicketInCart> getListTicketBooked(User user) {
		List<Order_detail> orderDetails = orderDetailDao.getAllOrderDetailsByUser(user);
		List<TicketInCart> ticketInCarts = new ArrayList<>();
		for (Order_detail item : orderDetails) {
			List<Order_item> orderItems = orderItemDao.findAllBookedTickets(item);
			for (Order_item orderItem : orderItems) {
				ticketInCarts.add(new TicketInCart(orderItem.getSeatStatus(),
						orderItem.getSeatStatus().getScreening().getMovie().getTitle(),
						orderItem.getSeatStatus().getScreening().getMovie().getDirector(),
						orderItem.getSeatStatus().getScreening().getMovie().getVerticalImage(),
						orderItem.getSeatStatus().getScreening().getMovie().getHorizontalImage(),
						orderItem.getSeatStatus().getScreening().getMovie().getId()));
			}
		}
		return ticketInCarts;
	}

	@Override
	public List<TicketInCart> getListTicketsBeforeBook(List<Long> ids) {
		List<SeatStatus> listSeats = seatStatusDao.findAllById(ids);
		List<TicketInCart> ticketInCarts = new ArrayList<>();
		for (SeatStatus item: listSeats) {
			ticketInCarts.add(new TicketInCart(item, 
					item.getScreening().getMovie().getTitle(), 
					item.getScreening().getMovie().getDirector(), 
					item.getScreening().getMovie().getVerticalImage(), 
					item.getScreening().getMovie().getHorizontalImage(), 
					item.getScreening().getMovie().getId()
					));
		}
		return ticketInCarts;
	}

	@Override
	@Transactional
	public boolean deleteTicketFromCart(User user, Long ticketId) {
		Cart userCart = cartDao.findCurrentActiveCartByUser(user).orElseThrow();
		Ticket ticket = ticketDao.findTicketInCurrentCartById(ticketId, userCart);
		try {
			ticketDao.delete(ticket);
			userCart.setTotalTicket(userCart.getTotalTicket() - 1);
			cartDao.save(userCart);
			return true;
		} catch (Exception e) {
			//System.out.print("ERROR!");
			return false;
		}
	}

}
