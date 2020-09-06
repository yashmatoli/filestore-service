//package com.filestore.service;
//
//import static org.mockito.Mockito.when;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.example.filestore.service.FileStoreService;
//
//
//@ExtendWith(MockitoExtension.class)
//class FilestoreServiceApplicationTests {
//
//	@InjectMocks
//	private FileStoreService fileStoreService;
//	
//	@Mock
//    private HttpServletRequest request;
//	
//	@Test
//	void listFiles() {
//		when(request.getServletContext().getRealPath(Mockito.anyString())).thenReturn("src\test\resources\files");
//		String[] fileNames = fileStoreService.getFileNames(request);
//		Assertions.assertNotNull(fileNames.length);
//		
//	}
//
//}
