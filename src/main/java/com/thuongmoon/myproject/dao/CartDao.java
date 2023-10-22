package com.thuongmoon.myproject.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thuongmoon.myproject.model.Cart;
import com.thuongmoon.myproject.model.User;


public interface CartDao extends JpaRepository<Cart, Long>  {
	//@Query("SELECT c FROM Cart c WHERE c.user = :user AND c.active = true")
	@Query("SELECT c FROM Cart c WHERE c.active = true AND c.user = :user")
	Optional<Cart> findCurrentActiveCartByUser(User user);
}
