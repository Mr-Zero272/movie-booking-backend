package com.thuongmoon.myproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thuongmoon.myproject.dto.CheckoutRequest;
import com.thuongmoon.myproject.dto.CheckoutResponse;
import com.thuongmoon.myproject.dto.ListIdBooking;
import com.thuongmoon.myproject.dto.SeatDto;
import com.thuongmoon.myproject.dto.TicketInCart;
import com.thuongmoon.myproject.message.ResponseMessage;
import com.thuongmoon.myproject.model.Cart;
import com.thuongmoon.myproject.model.User;
import com.thuongmoon.myproject.service.CartServiceImpl;
import com.thuongmoon.myproject.service.UserServiceImpl;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/cart")
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequiredArgsConstructor
public class CartController {
	@Autowired
	private final UserServiceImpl userService;
	@Autowired
	private final CartServiceImpl cartService;

	@GetMapping("/seats")
	public List<SeatDto> getListSeats(@RequestParam(required = true) Long screeningId, @RequestParam(required = true) Long auditoriumId) {
		List<SeatDto> seatDtos = cartService.getSeats(screeningId, auditoriumId);
		return seatDtos;
	}
	
	@GetMapping
	public List<TicketInCart> getListTicketInCart(@NonNull HttpServletRequest request) {
		String username = userService.getUsernameFromToken(request);
		//System.out.print(username + "\n");
		User user = userService.findUserByUsername(username).orElse(new User());
		List<TicketInCart> tickets = cartService.getListTicket(user);
		//System.out.print(tickets.get(0).getScreening().getMovie().getId());		
		return tickets;
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseMessage> deleteTicketInCart(@NonNull HttpServletRequest request, @RequestBody ListIdBooking listIdBooking) {
		try {
			User user = userService.getUserFromToken(request);
			boolean isDelete = cartService.deleteTicketFromCart(user, listIdBooking.getIds().get(0));
			if (isDelete) {
				return new ResponseEntity<ResponseMessage>(new ResponseMessage("success"), HttpStatus.OK);				
			} else {
				return new ResponseEntity<ResponseMessage>(new ResponseMessage("Something went wrong!"), HttpStatus.FAILED_DEPENDENCY);
			}
		} catch (Exception e) {
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Something went wrong!"), HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PostMapping
	public ResponseEntity<ResponseMessage> addToCart(@NonNull HttpServletRequest request, @RequestBody ListIdBooking listIdBooking) {
		try {
			String username = userService.getUsernameFromToken(request);
			User user = userService.findUserByUsername(username).orElse(new User());
			Cart userCart = cartService.getCurretCartFromUser(user); 
			cartService.addTicketToCart(listIdBooking.getIds(), userCart);
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Add to cart successfully!"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Something went wrong!"), HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@GetMapping("/checkout")
	public List<TicketInCart>  getListTicketOrdered(@NonNull HttpServletRequest request) {
		User user = userService.getUserFromToken(request);
		List<TicketInCart> tickets = cartService.getListTicketBooked(user);
		//System.out.print(tickets.get(0).getScreening().getMovie().getId());		
		return tickets;
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<CheckoutResponse> addTicketBooked(@NonNull HttpServletRequest request, @RequestBody CheckoutRequest checkoutRequest) {
		try {
			User user = userService.getUserFromToken(request);
			CheckoutResponse response= cartService.addTicketBooked(checkoutRequest.getIds(), checkoutRequest.isPaid(), user);
			return new ResponseEntity<CheckoutResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CheckoutResponse>(new CheckoutResponse(null, "Something went wrong!"), HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@GetMapping("/quantity")
	public ResponseEntity<Integer> getQuantityOfTicketInCart(@NonNull HttpServletRequest request) {
		try {
			String username = userService.getUsernameFromToken(request);
			User user = userService.findUserByUsername(username).orElse(new User());
			Cart userCart = cartService.getCurretCartFromUser(user);
			return new ResponseEntity<Integer>(userCart.getTotalTicket(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(0, HttpStatus.OK);
		}
	}
	
	@GetMapping("/tickets")
	public ResponseEntity<List<TicketInCart>> getListTicketsBeforeBook(@RequestParam(required = true) List<Long> ids) {
		System.out.print(ids.get(0) + "dfasfsadfasdfasdfasdf\n");
		try {
			return new ResponseEntity<List<TicketInCart>>(cartService.getListTicketsBeforeBook(ids), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
