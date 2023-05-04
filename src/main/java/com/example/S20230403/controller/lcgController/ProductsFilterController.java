package com.example.S20230403.controller.lcgController;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.S20230403.model.Accom;
import com.example.S20230403.service.lcgService.ProductsFilterService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductsFilterController {
	private final ProductsFilterService service;
	

	// 리펙토링 호텔빼고 리스트 다뽑아오기
	@GetMapping("/cgProductLists")
	public String cgGetProductByAccomtype(@RequestParam("accom-type") String accom_type, Accom accom, Model model) {
		System.out.println("리펙토링 cgProductByAccomtype 시작");
		System.out.println("리펙토링 cgProductByAccomtype accom_type->"+accom_type);
		List<Accom> cgProductLists = service.cgGetProductByAccomtype(accom_type);
		System.out.println("cgProductListWithoutHotel  사이즈 -> "+cgProductLists.size());
		
		// accom_type이 호텔들이면 호텔페이지로 넘어가게 했음.
		// 페이지를 나누는 이유는 ajax 작동방식이 다르고 필터바 내용도 다르기 때문
		if(accom_type.equals("511,513,514,515")) {
			model.addAttribute("products", cgProductLists);
			model.addAttribute("accom_type", accom_type);
			return "/views/productLists/cgProductHotelLists";
		}
		model.addAttribute("products", cgProductLists);
		model.addAttribute("accom_type", accom_type);		
		System.out.println("컨트롤러 /cgProduct/motel 끝");
		return "/views/productLists/cgProductListsWithoutHotel";
	}
		
	
	// 모텔, 게하, 펜션  지역별로 리스트 뽑아오기
	@GetMapping("cgProductListsByAddr")
	public String cgGetProductListsByAddr(@RequestParam("accom-type") String accom_type,
									   @RequestParam("addr") String addr,
									   Accom accom, Model model) {
		System.out.println("컨트롤러 cgProductListsByAddr 시작");
		accom.setAccom_type(accom_type);
		accom.setAddr(addr);
		System.out.println("컨트롤러 cgProductListsByAddr getAccom_type, addr-> "+accom.getAccom_type() +"," + accom.getAddr());
		List<Accom> cgProductListsByAddr = service.cgGetProductListsByAddr(accom);
		System.out.println("컨트롤러 cgProductListsByAddr 사이즈 -> "+cgProductListsByAddr.size());
		
		// accom_type이 호텔들이면 호텔페이지로 넘어가게 했음.
		// 페이지를 나누는 이유는 ajax 작동방식이 다르고 필터바 내용도 다르기 때문
		if(accom_type.equals("511,513,514,515")) {
			model.addAttribute("products", cgProductListsByAddr);
			model.addAttribute("accom_type", accom_type);
			model.addAttribute("addr", addr);
			return "/views/productLists/cgProductHotelListsByAddr";
		}
		model.addAttribute("products", cgProductListsByAddr);
		model.addAttribute("accom_type", accom_type);
		model.addAttribute("addr", addr);
		
		return "/views/productLists/cgProductListsWithoutHotelByAddr";
	}

}
