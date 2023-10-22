package com.thuongmoon.myproject.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	  public void init();

	  public void save(MultipartFile file);
	  
	  public void saveFileWithPath(Path path, MultipartFile file);

	  public Resource load(String filename);
	  
	  public Resource loadFileWithPath(Path root, String fileName);
	  
	  public boolean delete(String filename);
	  
	  public boolean deleteFileWithPath(Path root, String fileName);

	  public void deleteAll();

	  public Stream<Path> loadAll();
}
