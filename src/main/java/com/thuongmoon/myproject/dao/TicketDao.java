package com.thuongmoon.myproject.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thuongmoon.myproject.model.Cart;
import com.thuongmoon.myproject.model.Ticket;

import jakarta.transaction.Transactional;
import com.thuongmoon.myproject.model.SeatStatus;


public interface TicketDao extends JpaRepository<Ticket, Long> {
	@Query("SELECT t FROM Ticket t WHERE t.cart = :cart")
	List<Ticket> findAllTicketInCurrentCart(Cart cart);
	
	@Query("SELECT t FROM Ticket t WHERE t.id = :id AND t.cart = :cart")
	Ticket findTicketInCurrentCartById(Long id, Cart cart);
	
	@Query("SELECT t FROM Ticket t WHERE t.seatStatus = :seatStatus AND t.cart = :cart")
	Optional<Ticket> findBySeatStatus(SeatStatus seatStatus, Cart cart);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Ticket VALUES(:cartId, :seatStatusId)", nativeQuery = true)
	void addTicketToCart(@Param("cartId") Long cartId, @Param("seatStatusId") Long seatSeatusId);
}
