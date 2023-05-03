package com.example.S20230403.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SbController {
	@GetMapping("/")
	public String root() {
		return "/views/main";
	}

	@GetMapping("redirect-page")
	public String denied() {
		return "redirect-page";
	}

	@PostMapping("redirect-page")
	public String deniedPost() {
		return "redirect-page";
	}

	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin page";
	}
	@GetMapping("/users/myPage")
	@ResponseBody
	public String mypage_user() {
		return "마이페이지 유저";
	}
	@GetMapping("/seller/myPage")
	@ResponseBody
	public String mypage_seller() {
		return "마이페이지 셀러";
	}
}
