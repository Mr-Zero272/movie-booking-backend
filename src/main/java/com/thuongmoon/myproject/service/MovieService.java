package com.thuongmoon.myproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.thuongmoon.myproject.dto.ManufacturersDto;
import com.thuongmoon.myproject.model.Movie;

public interface MovieService {
	public Page<Movie> searchMovies(String q, Pageable pageable);

	public Page<Movie> findAllMovies(Pageable pageable);

	public Page<Movie> getPaginationMovie(Pageable pageable, String q, 
			String type, List<Long> genres, List<String> manufacturers);
	public Page<Movie> getAllTodayMovies(Pageable pageable, String q, String genre);
	
	public List<ManufacturersDto> getManufacturers();
	
	public Optional<Movie> findMovieById(Long id);
}
