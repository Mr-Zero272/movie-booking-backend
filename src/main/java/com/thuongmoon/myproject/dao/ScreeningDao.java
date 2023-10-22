package com.thuongmoon.myproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thuongmoon.myproject.model.Screening;

public interface ScreeningDao extends JpaRepository<Screening, Long>{
	
	@Query("SELECT DISTINCT s.type FROM Screening s")
	List<String> findAllScreeningTypes();
}
