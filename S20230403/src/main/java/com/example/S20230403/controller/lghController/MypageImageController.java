package com.example.S20230403.controller.lghController;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
//@RequiredArgsConstructor
public class MypageImageController {
	/*@Override
	public ModelAndView reviewImgUpload(MultipartHttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		// view에서 보내는 파일 이름??
		MultipartFile uploadFile = request.getFile("savedName");
		
		// 파일의 원래 이름
		String originalFileName = uploadFile.getOriginalFilename();
		
		// 파일의 확장자 추출
		String extension = originalFileName.substring(originalFileName.indexOf("."));
		
		// 중복 파일명 방지(UUID 사용)
		String newFileName = UUID.randomUUID() + extension;
		
		int index = System.getProperty("user.dir").indexOf("\\S20230403");
		String path = System.getProperty("user.dir").substring(0, index);
		
		// 저장 경로 지정?
		String savePath = path + "/uploadImage/" + newFileName;
		
		// 해당 파일 경로에 폴더가 없을시 폴더 생성
		File fileDirectory = new File(savePath);
		if (!fileDirectory.exists()) {
			// 신규 폴더(디렉토리) 생성
			fileDirectory.mkdirs();
		}
		
		String uploadPath = "/uploadImage/" + newFileName;
		
		// 저장 경로로 파일 객체 생성
	      File file = new File(savePath);
	      
	      // 파일 업로드
	      try {
	         uploadFile.transferTo(file);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      // uploaded, url 값을 Modelandview를 통해 보냄
	      mav.addObject("uploaded", true); // 업로드 완료
	      mav.addObject("url", uploadPath); // 업로드 완료
	      return mav;
	}*/
}
