package com.thuongmoon.myproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thuongmoon.myproject.model.Auditorium;
import com.thuongmoon.myproject.model.SeatStatus;

public interface SeatStatusDao extends JpaRepository<SeatStatus, Long> {
	@Query("select ss.id, ss.status, s.numberSeat, s.rowSeat, ss.price from SeatStatus ss join ss.screening sc join ss.seat s where sc.id = :screeningId AND sc.auditorium = :auditorium")
	List<String[]> findSeatListByScreeningStartAndAuditorium(@Param("screeningId") Long screeningId, @Param("auditorium") Auditorium auditorium);

}
