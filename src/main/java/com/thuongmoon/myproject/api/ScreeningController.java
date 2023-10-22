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

import com.thuongmoon.myproject.dto.ScreeningTypeDto;
import com.thuongmoon.myproject.service.ScreeningService;

import lombok.AllArgsConstructor;

@RequestMapping("/api/v1/screening")
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@AllArgsConstructor
public class ScreeningController {
	private final ScreeningService screeningService;
	
	@GetMapping("/type")
	public ResponseEntity<Map<String, Object>> getAllScreeningType() {
		List<ScreeningTypeDto> types = screeningService.getAllScreeningTypes();
		Map<String, Object> response = new HashMap<>();
		response.put("types", types);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
