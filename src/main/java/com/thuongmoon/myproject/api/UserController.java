package com.thuongmoon.myproject.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thuongmoon.myproject.config.JwtService;
import com.thuongmoon.myproject.dto.UserDto;
import com.thuongmoon.myproject.message.ResponseMessage;
import com.thuongmoon.myproject.model.AuthenticationResponse;
import com.thuongmoon.myproject.model.User;
import com.thuongmoon.myproject.service.FilesStorageService;
import com.thuongmoon.myproject.service.UserServiceImpl;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserServiceImpl userService;
	private final JwtService jwtService;
	private final FilesStorageService storageService;
	
	@GetMapping
	public ResponseEntity<UserDto> getUserByUsername(@NonNull HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String username;
		jwt = authHeader.substring(7);
		username = jwtService.extractUsername(jwt);
		Optional<User> userPr = userService.findUserByUsername(username);
		User tempUser = userPr.orElse(new User());
		UserDto userPl = new UserDto();
		userPl.setUsername(tempUser.getUsername());
		userPl.setEmail(tempUser.getEmail());
		userPl.setAvatar(tempUser.getAvatar());
		userPl.setId(tempUser.getId());
		userPl.setPhone(tempUser.getPhoneNumber());
		return new ResponseEntity<>(userPl, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<AuthenticationResponse> updateUser(@NonNull HttpServletRequest request, 
			@RequestPart("newUsername") String newUsername, 
			@RequestPart("newEmail") String newEmail, 
			@RequestPart("newPhoneNumber") String newPhoneNumber) {
		String username = userService.getUsernameFromToken(request); 
		return ResponseEntity.ok(userService.updateUser(username, newUsername, newEmail, newPhoneNumber));
	}
	
	@PostMapping("/avatar")
	public ResponseEntity<ResponseMessage> updateAvatarImage(@RequestPart("file") MultipartFile file, @NonNull HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String username;
		jwt = authHeader.substring(7);
		username = jwtService.extractUsername(jwt);
		String fileName = file.getOriginalFilename();
		String message = "";
		Path imagePath = Paths.get("uploads/images/avatars");	
		try {
			if(!userService.updateAvatar(username, fileName))
			{
				message = "Something went wrong!!!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
			storageService.saveFileWithPath(imagePath, file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
}
