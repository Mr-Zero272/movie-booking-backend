package com.thuongmoon.myproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thuongmoon.myproject.model.Order_detail;
import com.thuongmoon.myproject.model.Order_item;

public interface OrderItemDao extends JpaRepository<Order_item, Long> {
	@Query("SELECT oi FROM Order_item oi WHERE oi.orderDetail = :orderDetail")
	List<Order_item> findAllBookedTickets(Order_detail orderDetail);
}
