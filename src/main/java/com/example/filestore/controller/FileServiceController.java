package com.example.filestore.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.filestore.service.FileStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="FileStore Endpoints", description="Endpoints for Export, Import and List the files")
public class FileServiceController {

	private static final Logger logger = LoggerFactory.getLogger(FileServiceController.class);

	@Autowired
	private FileStoreService fileStorageService;

	@ApiOperation(value="Upload (*.tar) files into the FileStore")
	@ApiResponses(
			value={
					@ApiResponse(code=500, message="Something went wrong! while storing the file")
			})
	@PostMapping("/importFiles")
	public ResponseEntity<String> importFiles(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		List<String> collectFileNames = Arrays.asList(files).stream().map(file -> fileStorageService.uploadFile(file, request))
				.collect(Collectors.toList());
		logger.debug(collectFileNames.toString());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				.body("Following files uploaded successfully: " + collectFileNames.toString());
	}

	@ApiOperation(value="Download (*.tar) files from the FileStore")
	@ApiResponses(
			value={
					@ApiResponse(code=404, message="File not found!"),
					@ApiResponse(code=500, message="Something went wrong! while storing the file")
			})
	@GetMapping("/exportFile/{fileName:.+}")
	public ResponseEntity<Resource> exportFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = fileStorageService.loadFileAsResource(fileName, request);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@ApiOperation(value="List files from the FileStore")
	@ApiResponses(
			value={
					@ApiResponse(code=500, message="Something went wrong! while storing the file")
			})
	@GetMapping("/listFiles")
	public ResponseEntity<String[]> listFiles(HttpServletRequest request) {
		String[] fileNames = fileStorageService.getFileNames(request);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/json")).body(fileNames);
	}
}