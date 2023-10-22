package com.thuongmoon.myproject.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thuongmoon.myproject.model.User;

public interface UserDao extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username <> :username AND u.username = :newUsername")
	Optional<User> findIfUsernameExisted(String username, String newUsername);
}
