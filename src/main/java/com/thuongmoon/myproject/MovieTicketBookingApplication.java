package com.thuongmoon.myproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thuongmoon.myproject.service.FilesStorageService;

import jakarta.annotation.Resource;

@SpringBootApplication
public class MovieTicketBookingApplication implements CommandLineRunner {

	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(MovieTicketBookingApplication.class, args);
	}
	
	@Override
	public void run(String... arg) throws Exception {
//	    storageService.deleteAll();
		storageService.init();
	}
}
