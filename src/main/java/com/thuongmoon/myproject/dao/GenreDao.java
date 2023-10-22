package com.thuongmoon.myproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thuongmoon.myproject.model.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

}
