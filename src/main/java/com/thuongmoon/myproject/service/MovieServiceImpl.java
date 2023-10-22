package com.thuongmoon.myproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thuongmoon.myproject.dao.MovieDao;
import com.thuongmoon.myproject.dto.ManufacturersDto;
import com.thuongmoon.myproject.model.Movie;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private final MovieDao movieDao;

	public MovieServiceImpl(MovieDao movieDao) {
		this.movieDao = movieDao;
	}
	
	@Override
	public Page<Movie> searchMovies(String q, Pageable pageable) {
		return movieDao.findByName(q, pageable);
	}
	
	@Override
	public Page<Movie> findAllMovies(Pageable pageable) {
		return movieDao.findAllMovies(pageable);
	}

	@Override
	public Page<Movie> getPaginationMovie(Pageable pageable, String q, String type, List<Long> genres,
			List<String> manufacturers) {
		if (q == null || q == "" || q.isEmpty()) q = "%";
		if (type == "" || type == null || type.isEmpty()) type = "%";
		if (manufacturers == null && genres == null) {
			System.out.print(type + "\n");
			System.out.print(q + "\n");
			return movieDao.getMoviePaginationPageWithoutManufacturersAndGenres(pageable, q, type);
		} else if (manufacturers == null) {
			return movieDao.getMoviePaginationPageWithouManufacturers(pageable, q, type, genres);
		} else if (genres == null) {
			return movieDao.getMoviePaginationPageWithouGenres(pageable, q, type, manufacturers);
		} else {
			return movieDao.getMoviePaginationPage(pageable, q, type, genres, manufacturers);
		}
		//return movieDao.getMoviePaginationPage(pageable, q, type, genres, manufacturers);
	}

	@Override
	public List<ManufacturersDto> getManufacturers() {
		List<ManufacturersDto> listManufacturers = new ArrayList<>();
		List<String> allManufacturers = movieDao.getManufacturers();
		for (int i = 0; i < allManufacturers.size(); i++) {
			listManufacturers.add(new ManufacturersDto(allManufacturers.get(i)));
		}
		return listManufacturers;
	}

	@Override
	public Page<Movie> getAllTodayMovies(Pageable pageable, String q, String genre) {
		String today = java.time.LocalDateTime.now().toString();
		if (genre == "" || genre == null || genre.isEmpty()) genre = "%";
		if (q == null || q == "" || q.isEmpty()) q = "%";
		return movieDao.getMoviesForSchudulePage(pageable, q, genre, today);
	}

	@Override
	public Optional<Movie> findMovieById(Long id) {
		Optional<Movie> movie = Optional.ofNullable(movieDao.findById(id).orElseThrow());
		return movie;
	}

}
