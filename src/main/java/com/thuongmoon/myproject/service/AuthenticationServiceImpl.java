package com.thuongmoon.myproject.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thuongmoon.myproject.config.JwtService;
import com.thuongmoon.myproject.dao.UserDao;
import com.thuongmoon.myproject.model.AuthenticationResponse;
import com.thuongmoon.myproject.model.AuthenticationResquest;
import com.thuongmoon.myproject.model.RegisterRequest;
import com.thuongmoon.myproject.model.Role;
import com.thuongmoon.myproject.model.User;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserDao repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	

	public AuthenticationServiceImpl(UserDao repository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.avatar(request.getAvatar())
				.phoneNumber(request.getPhoneNumber())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		var jwtToken = jwtService.generateToken(user);
		repository.save(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.message("success")
				.build();
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationResquest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(), 
						request.getPassword()
				)
		);
		var user = repository.findByUsername(request.getUsername()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.message("success")
				.build();
	}

}
