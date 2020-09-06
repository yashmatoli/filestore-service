package com.example.filestore.service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.filestore.exception.FileNotFoundException;
import com.example.filestore.exception.FileStoreException;

@Service
public class FileStoreService {

	private static final Logger logger = LoggerFactory.getLogger(FileStoreService.class);

	@Value("${file.store.dir}")
	public String rootDirectory;

	public String uploadFile(MultipartFile file,HttpServletRequest request) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (!fileName.contains(".tar")) {
			throw new FileStoreException("Incorrect File! Please import tar files only");
		} else {
			try {
				Path copyLocation = Paths
						.get(request.getServletContext().getRealPath("/") + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
				Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
				logger.info(copyLocation.toString());
			} catch (Exception e) {
				logger.info("Failed to store file : " + file.getOriginalFilename());
				throw new FileStoreException("Failed to store file: " + file.getOriginalFilename());
			}
			return fileName;
		}
	}

	public Resource loadFileAsResource(String fileName,HttpServletRequest request) {
		if (!fileName.contains(".tar")) {
			throw new FileStoreException("Incorrect File requested! Please export tar files only");
		} else {
			try {
				Path filePath = Paths.get(request.getServletContext().getRealPath("/") + File.separator + StringUtils.cleanPath(fileName));
				Resource resource = new UrlResource(filePath.toUri());
				if (resource.exists()) {
					logger.info(filePath.toString());
					return resource;
				} else {
					logger.info("File not found " + fileName);
					throw new FileNotFoundException("File not found " + fileName);
				}
			} catch (MalformedURLException ex) {
				throw new FileNotFoundException("Could not parse path" + fileName, ex);
			}
		}
	}

	public String[] getFileNames(HttpServletRequest request) {
		logger.info(request.getServletContext().getRealPath("/"));
		String[] fileNames;
		File f = new File(request.getServletContext().getRealPath("/"));
		fileNames = f.list();
		logger.info(fileNames.toString());
		return fileNames;
	}
}
