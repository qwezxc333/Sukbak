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
		List<Accom> cgProductListsWithoutHotel = service.cgGetProductByAccomtype(accom_type);
		System.out.println("cgProductListWithoutHotel  사이즈 -> "+cgProductListsWithoutHotel.size());
		model.addAttribute("products", cgProductListsWithoutHotel);
		model.addAttribute("accom_type", accom_type);		
		System.out.println("컨트롤러 /cgProduct/motel 끝");
		return "/views/productLists/cgProductListsWithoutHotel";
	}
		
	// 리펙토링 호텔 리스트 뽑아오기
	@GetMapping("/cgProductLists/hotel")
	public String cgGetProductHotelList(Accom accom, Model model) {
		System.out.println("컨트롤러 cgSearch_hotel 시작");
		List<Accom> cgProductHotelLists = service.cgGetProductHotelLists();
		System.out.println("컨트롤러 cgSearch_hotel 사이즈 -> "+cgProductHotelLists.size());
		model.addAttribute("products", cgProductHotelLists);
		System.out.println("컨트롤러 cgSearch_hotel 끝");
		return "/views/productLists/cgProductHotelLists";
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
		model.addAttribute("products", cgProductListsByAddr);
		model.addAttribute("accom_type", accom_type);
		model.addAttribute("addr", addr);
		
		return "/views/productLists/cgProductListsWithoutHotelByAddr";
	}
	@GetMapping("cgProductHotelListsByAddr")
	public String cgGetProductHotelListsByAddr(@RequestParam("addr") String addr, Accom accom, Model model) {
		System.out.println("컨트롤러 cgProductHotelListsByAddr 시작");
		System.out.println("컨트롤러 cgProductHotelListsByAddr 주소-> "+addr);
		List<Accom> cgProductHotelListsByAddr = service.cgGetProductHotelListsByAddr(addr);
		System.out.println("cgProductHotelListsByAddr 사이즈 -> "+ cgProductHotelListsByAddr.size());
		model.addAttribute("products", cgProductHotelListsByAddr);
		model.addAttribute("addr", addr);
		
		return "/views/productLists/cgProductHotelListsByAddr";
	}

}
