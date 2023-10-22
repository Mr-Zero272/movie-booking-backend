package com.thuongmoon.myproject.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thuongmoon.myproject.model.Genre;
import com.thuongmoon.myproject.service.GenreService;

import lombok.AllArgsConstructor;

@RequestMapping("/api/v1/genre")
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@AllArgsConstructor
public class GenreController {
	
	private final GenreService genreService;
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllGenre() {
		List<Genre> genres = genreService.getAllGenres();
		
		Map<String, Object> response = new HashMap<>();
		response.put("genres", genres);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
