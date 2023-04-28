package com.example.S20230403.controller.lcgController;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.model.ChanJoin;
import com.example.S20230403.service.lcgService.ProductsFilterService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductsFilterRestController {
	private final ProductsFilterService service;
	
	@RequestMapping("/cgAjaxProductList")
	public List<ChanJoin> cgGetAjaxProductListsWithoutHotel(ChanJoin chanJoin){
		System.out.println("ajax cgProductList  시작");
		System.out.println("ajax cgProductList getKind->"+chanJoin.getKind());
		System.out.println("ajax cgProductList getAccom_type->"+chanJoin.getAccom_type());
		System.out.println("ajax cgProductList getCheck_in->"+chanJoin.getCheck_in());
		System.out.println("ajax cgProductList getCheck_out->"+chanJoin.getCheck_out());
		System.out.println("ajax cgProductList getBed_type->"+chanJoin.getBed_type());
		System.out.println("ajax cgProductList getCafe->"+chanJoin.getCafe());
		System.out.println("ajax cgProductList getStore->"+chanJoin.getStore());
		System.out.println("ajax cgProductList getFitness->"+chanJoin.getFitness());
		System.out.println("ajax cgProductList getcapacity->"+chanJoin.getR_capacity());

	
		if (chanJoin.getKind() == 1) {
			System.out.println("ajax cgProductList 인기순");
		}else if(chanJoin.getKind() == 2) {
			System.out.println("ajax cgProductList 낮은가격순");
		}else if(chanJoin.getKind() == 3) {
			System.out.println("ajax cgProductList 높은가격순");
		}
		
		List<ChanJoin> cgAjaxProductListsWithoutHotel = service.cgGetAjaxProductListsWithoutHotel(chanJoin);
		System.out.println("ajax cgProductList  사이즈 -> "+cgAjaxProductListsWithoutHotel.size());
		System.out.println("ajax cgProductList  끝");
		return cgAjaxProductListsWithoutHotel;
	}
	

	@RequestMapping("/cgAjaxProductHotelList")
	public List<ChanJoin> cgGetAjaxProductHotelLists(ChanJoin chanJoin){
		System.out.println("ajax cgAjaxProductHotelList 시작");
		System.out.println("ajax cgAjaxProductHotelList getAccom_type->"+chanJoin.getAccom_type());
		System.out.println("ajax cgAjaxProductHotelList getBed_type->"+chanJoin.getBed_type());
		List<ChanJoin> cgAjaxProductHotelLists = service.cgGetAjaxProductHotelLists(chanJoin);
		System.out.println("ajax cgAjaxProductHotelList 사이즈 -> "+cgAjaxProductHotelLists.size());
		System.out.println("ajax cgAjaxProductHotelList 끝");
		return cgAjaxProductHotelLists;
	}

}
