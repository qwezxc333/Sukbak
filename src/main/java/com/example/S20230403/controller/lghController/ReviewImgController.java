package com.example.S20230403.controller.lghController;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewImgController {
	
	@GetMapping("/display")
	public ResponseEntity<Resource> GetReviewImage(@RequestParam("file") String fileName){
		
		System.out.println("fileName -> "+fileName);
		
		String absoultePath = new File("").getAbsolutePath()+File.separator;
		
		//byte 값을 리소스에 대입
		Resource resourse = new FileSystemResource(absoultePath+fileName);
		if(!resourse.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		System.out.println("파일업로드 리소스 -> "+resourse);
		HttpHeaders header = new HttpHeaders();
		
		try {
			Path filePath = Paths.get(absoultePath+fileName);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<Resource>(resourse, header, HttpStatus.OK);
	}
}
