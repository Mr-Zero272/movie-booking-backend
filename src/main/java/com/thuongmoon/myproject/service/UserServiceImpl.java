package com.thuongmoon.myproject.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thuongmoon.myproject.config.JwtService;
import com.thuongmoon.myproject.dao.UserDao;
import com.thuongmoon.myproject.model.AuthenticationResponse;
import com.thuongmoon.myproject.model.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	@Autowired
	private final UserDao userDao;
	private final JwtService jwtService;
	private final FilesStorageServiceImpl filesStorageService;
	
	@Override
	public List<User> getAllUser() {
		return userDao.findAll();
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public boolean updateAvatar(String username, String avatar) {
		User user = userDao.findByUsername(username).orElse(new User());
		Path imagePath = Paths.get("uploads/images/avatars");
		if (user.getAvatar().equals("no_image.png")) {
			user.setAvatar(avatar);
			userDao.save(user);
			return true;
		} else {
			if(filesStorageService.deleteFileWithPath(imagePath, user.getAvatar())) {
				user.setAvatar(avatar);
				userDao.save(user);
				return true;
			} else {
				return false;
			}			
		}
	}

	@Override
	public String getUsernameFromToken(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String username;
		jwt = authHeader.substring(7);
		username = jwtService.extractUsername(jwt);
		return username;
	}

	@Override
	public AuthenticationResponse updateUser(String username, String newUsername, String newEmail, String newPhoneNumber) {
		Optional<User> user = userDao.findIfUsernameExisted(username, newUsername);
		String jwtToken = "";
		if(user.isEmpty()) {
			User updateUser = userDao.findByUsername(username).orElse(new User());
			updateUser.setUsername(newUsername);
			updateUser.setEmail(newEmail);
			updateUser.setPhoneNumber(newPhoneNumber);
			userDao.save(updateUser);
			jwtToken = jwtService.generateToken(updateUser);
			return AuthenticationResponse.builder()
					.token(jwtToken)
					.message("success")
					.build();
		} else {
			return AuthenticationResponse.builder()
					.token(jwtToken)
					.message("This username is already existed!!")
					.build();
		}
	}

	@Override
	public User getUserFromToken(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String username;
		jwt = authHeader.substring(7);
		username = jwtService.extractUsername(jwt);
		User user = userDao.findByUsername(username).orElseThrow();
		return user;
	}


}
