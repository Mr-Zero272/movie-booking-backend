package com.thuongmoon.myproject.service;

import java.util.List;
import java.util.Optional;

import com.thuongmoon.myproject.model.AuthenticationResponse;
import com.thuongmoon.myproject.model.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	public List<User> getAllUser();
	
	public Optional<User> findUserByUsername(String username);
	
	public boolean updateAvatar(String username, String avatar);
	
	public String getUsernameFromToken(HttpServletRequest request);
	
	public User getUserFromToken(HttpServletRequest request);
	
	public AuthenticationResponse updateUser(String username, String newUsername, String newEmail, String newPhoneNumber);
}
