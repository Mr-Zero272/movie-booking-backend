package com.thuongmoon.myproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thuongmoon.myproject.model.Order_detail;
import com.thuongmoon.myproject.model.User;

public interface OrderDetailDao extends JpaRepository<Order_detail, Long> {
	@Query("SELECT od FROM Order_detail od WHERE od.user = :user AND od.paid = true")
	List<Order_detail> getAllOrderDetailsByUser(User user);

}
