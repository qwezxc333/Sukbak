package com.example.S20230403.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SbController {
	@GetMapping("/")
	public String root() {
		return "/views/main";
	}

	@GetMapping("denied-page")
	public String denied() {
		return "redirect-page";
	}
	@PostMapping("denied-page")
	public String deniedPost() {
		return "redirect-page";
	}
}
