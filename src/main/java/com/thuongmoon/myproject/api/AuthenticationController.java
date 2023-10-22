package com.thuongmoon.myproject.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thuongmoon.myproject.model.AuthenticationResponse;
import com.thuongmoon.myproject.model.AuthenticationResquest;
import com.thuongmoon.myproject.model.RegisterRequest;
import com.thuongmoon.myproject.model.User;
import com.thuongmoon.myproject.service.AuthenticationServiceImpl;
import com.thuongmoon.myproject.service.UserServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	private final AuthenticationServiceImpl authenticationService;
	private final UserServiceImpl userService;
	
	public AuthenticationController(AuthenticationServiceImpl authenticationService, UserServiceImpl userService) {
		this.authenticationService = authenticationService;
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request){
		Optional<User> user = userService.findUserByUsername(request.getUsername());
//		System.out.println("username:" + request.getUsername());
//		System.out.println(user);
		if (user.isPresent()) {
			String message = "This username is already existed!!";
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthenticationResponse("", message));
			//return ResponseEntity.ok(new AuthenticationResponse("", message));
		} else {
			return ResponseEntity.ok(authenticationService.register(request));						
		}
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register (@RequestBody AuthenticationResquest request){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}
