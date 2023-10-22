package com.thuongmoon.myproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thuongmoon.myproject.model.Auditorium;

public interface AuditoriumDao extends JpaRepository<Auditorium, Long> {

}
