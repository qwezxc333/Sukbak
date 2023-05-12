package com.example.S20230403.controller.lysController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;
import com.example.S20230403.service.lysService.DefaultService;
import com.example.S20230403.service.lysService.Paging;
import com.example.S20230403.service.lysService.Service06;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DefaultController {

	private final DefaultService defaultService;

	@GetMapping("search")
	public String search(Model model,Accom accom,String sech) {
		List<Accom> searchAc =defaultService.searchAc(accom);
		System.out.println("검색 컨트롤라 시작");
		model.addAttribute("searchAc",searchAc);
		return "/views/heardSearch";
	}
}
