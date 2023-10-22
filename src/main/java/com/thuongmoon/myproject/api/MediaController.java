package com.thuongmoon.myproject.api;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thuongmoon.myproject.service.FilesStorageServiceImpl;

import lombok.RequiredArgsConstructor;

@RequestMapping("/movie")
@RestController
@RequiredArgsConstructor
public class MediaController {
	private final FilesStorageServiceImpl filesStorageService;
	
	@GetMapping("/images/{imageName}")
	public @ResponseBody ResponseEntity<Resource> downloadImageFromFileSystem(@PathVariable("imageName") String imageName, @RequestParam(required = false, defaultValue = "") String type) throws IOException {
		//String imagePath = "D:\\HockyI_nam4\\Nien_luan_co_so\\csdl_booking_movie\\images\\movies\\" + imageName;
		//byte[] image = Files.readAllBytes(new File(imagePath).toPath());
		Path imagePath;
		if (type.equals("avatar"))
		{
			imagePath = Paths.get("uploads/images/avatars");		
		} else {
			imagePath = Paths.get("uploads/images/movies");			
		}
		Resource image = filesStorageService.loadFileWithPath(imagePath, imageName);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		// Create and return a ResponseEntity object with the image bytes
		return new ResponseEntity<>(image, headers, HttpStatus.OK);
	}
	
	@GetMapping("/videos/{videoName}")
	public @ResponseBody ResponseEntity<Resource> getVideoFromFileSystem(@PathVariable("videoName") String videoName) throws IOException {
		//Path videoPath = Paths.get("uploads/videos/trailer");
//		String videoPath = "D:\\HockyI_nam4\\Nien_luan_co_so\\csdl_booking_movie\\videos\\trailer\\" + videoName;
//		byte[] video = Files.readAllBytes(new File(videoPath).toPath());
		Path imagePath = Paths.get("uploads/videos/trailer");
		Resource video = filesStorageService.loadFileWithPath(imagePath, videoName);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<>(video, headers, HttpStatus.OK);
	}
}
