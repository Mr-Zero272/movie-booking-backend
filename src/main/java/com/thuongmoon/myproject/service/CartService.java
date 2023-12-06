package com.thuongmoon.myproject.service;

import java.util.List;

import com.thuongmoon.myproject.dto.CheckoutRequest;
import com.thuongmoon.myproject.dto.CheckoutResponse;
import com.thuongmoon.myproject.dto.SeatDto;
import com.thuongmoon.myproject.dto.TicketInCart;
import com.thuongmoon.myproject.model.Cart;
import com.thuongmoon.myproject.model.User;

public interface CartService {
	public Cart getCurretCartFromUser(User user);
	
	public List<TicketInCart> getListTicket(User user);
	
	public List<TicketInCart> getListTicketBooked(User user);
	
	public List<TicketInCart> getListTicketsBeforeBook(List<Long> ids);
	
	public void addTicketToCart(List<Long> seatStatusIds, Cart userCart);
	
	public CheckoutResponse addTicketBooked(CheckoutRequest checkoutRequest, User user);
	
	public void createdInvoice(boolean paid, String invoiceId, User user);
	
	public boolean isInvoiceExists(String invoiceId);
	
	public List<SeatDto> getSeats(Long screeningId, Long auditoriumId);
	
	public boolean deleteTicketFromCart(User user, Long ticketId);
}
