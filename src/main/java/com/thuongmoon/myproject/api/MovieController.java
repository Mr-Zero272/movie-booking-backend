package com.thuongmoon.myproject.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thuongmoon.myproject.dto.ManufacturersDto;
import com.thuongmoon.myproject.model.Movie;
import com.thuongmoon.myproject.service.MovieService;

@RequestMapping("api/v1/movie")
@CrossOrigin(origins = "http://localhost:3001")
@RestController
public class MovieController {
	@Autowired
	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> getMovies(
			@RequestParam(required = false) String q,
			@RequestParam(defaultValue = "less") String type, 
			@RequestParam int cpage) {
		int size = type.equals("less") ? 6 : 12;
		Pageable pageable = PageRequest.of(cpage - 1, size);
		Page<Movie> page;
		if (q == null) {
			page = movieService.findAllMovies(pageable);
		} else {
			page = movieService.searchMovies(q, pageable);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("movies", page.getContent());
		response.put("totalPages", page.getTotalPages());
		response.put("currentPage", page.getNumber());
		response.put("size", page.getSize());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/pagination")
	public ResponseEntity<Map<String, Object>> getPaginationMoviePage(
			@RequestParam(required = false) String q,
			@RequestParam int size, 
			@RequestParam int cpage, 
			@RequestParam(required = false) List<Long> genres,
			@RequestParam String type, 
			@RequestParam(required = false) List<String> manufacturers) {
		Pageable pageable = PageRequest.of(cpage - 1, size);
		Page<Movie> page;

		page = movieService.getPaginationMovie(pageable, q, type, genres, manufacturers);

		Map<String, Object> response = new HashMap<>();
		response.put("movies", page.getContent());
		response.put("totalPages", page.getTotalPages());
		response.put("currentPage", page.getNumber());
		response.put("totalElements", page.getTotalElements());
		response.put("size", page.getSize());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/manufacturers")
	public ResponseEntity<Map<String, Object>> getAllManufacturers() {
		List<ManufacturersDto> manufacturers = movieService.getManufacturers();
		Map<String, Object> response = new HashMap<>();
		response.put("manufacturers", manufacturers);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/schedule")
	public ResponseEntity<Map<String, Object>> getTodaySchedule(
			@RequestParam(required = false) String q,
			@RequestParam int size, 
			@RequestParam int cpage,
			@RequestParam(required = false) String genre) {
		Pageable pageable = PageRequest.of(cpage - 1, size);
		Page<Movie> page;

		page = movieService.getAllTodayMovies(pageable, q, genre);
		
		Map<String, Object> response = new HashMap<>();
		response.put("movies", page.getContent());
		response.put("totalPages", page.getTotalPages());
		response.put("currentPage", page.getNumber());
		response.put("totalElements", page.getTotalElements());
		response.put("size", page.getSize());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{movieId}")
	public ResponseEntity<Optional<Movie>> getMovieById(@PathVariable("movieId") Long movieId) {
		Optional<Movie> movie = movieService.findMovieById(movieId);
		return new ResponseEntity<Optional<Movie>>(movie, HttpStatus.OK);
	}
}