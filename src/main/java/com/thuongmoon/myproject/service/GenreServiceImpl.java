package com.thuongmoon.myproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thuongmoon.myproject.dao.GenreDao;
import com.thuongmoon.myproject.model.Genre;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreDao genreDao;
	@Override
	public List<Genre> getAllGenres() {
		return genreDao.findAll();
	}

}
