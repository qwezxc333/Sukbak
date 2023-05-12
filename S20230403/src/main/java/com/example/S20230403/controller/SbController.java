package com.example.S20230403.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.model.Accom;
import com.example.S20230403.service.lysService.DefaultService;

@Controller
@RequiredArgsConstructor
public class SbController {
	private final DefaultService defaultService;
	@GetMapping("/")
	public String root() {
		return "/views/main";
	}

	@GetMapping("redirect-page")
	public String redirect() {
		return "redirect-page";
	}

	@PostMapping("redirect-page")
	public String redirectPost() {
		return "redirect-page";
	}

	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin_page";
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

	@GetMapping("/error2")
	public String error() {
		
		return "/views/error2";
	}
}
