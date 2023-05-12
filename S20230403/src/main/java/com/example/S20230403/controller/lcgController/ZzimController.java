package com.example.S20230403.controller.lcgController;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Accom;
import com.example.S20230403.service.lcgService.ZzimService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ZzimController {
	private final ZzimService service;
	
	@GetMapping("/commonUser/myZzim")
	public String getMyAccomZzimListsByUser_id(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("컨트롤러 getMyZzim 시작");
		// 로그인 아이디 넣기 
		String user_id = userDetail.getUsername();
		
		// 권한 넣어주기
		Collection<? extends GrantedAuthority> auth = userDetail.getAuthorities();
		
		List<Accom> myAccomZzimListsByUser_id = service.getMyAccomZzimListsByUser_id(user_id);
		
		
		System.out.println("컨트롤러 myAccomZzimLists 사이즈 3나와야됨  -> "+myAccomZzimListsByUser_id.size());
		System.out.println("컨트롤러 myAccomZzimLists 사이즈 3나와야됨  -> "+myAccomZzimListsByUser_id);
		System.out.println("컨트롤러 getMyZzim 유저 아디 -> "+user_id);
		
		model.addAttribute("products", myAccomZzimListsByUser_id);
		model.addAttribute("auth", auth);
		model.addAttribute("user_id", user_id);
		
		return "/views/myZzim/myZzim";
	}
}
