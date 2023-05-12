package com.example.S20230403.controller.lcgController;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.ChanJoin;
import com.example.S20230403.service.lcgService.ProductsFilterService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductsFilterRestController {
	private final ProductsFilterService service;
	
	@RequestMapping("/cgAjaxProductList")
	public List<ChanJoin> cgGetAjaxProductListsByAccomtypeAndAddr(@AuthenticationPrincipal PrincipalDetail userDetail, ChanJoin chanJoin){
//		System.out.println("ajax cgProductList  시작");
//		System.out.println("ajax cgProductList getKind->"+chanJoin.getKind());
//		System.out.println("ajax cgProductList getAccom_type->"+chanJoin.getAccom_type());
//		System.out.println("ajax cgProductList getCheck_in->"+chanJoin.getCheck_in());
//		System.out.println("ajax cgProductList getCheck_out->"+chanJoin.getCheck_out());
//		System.out.println("ajax cgProductList getBed_type->"+chanJoin.getBed_type());
//		System.out.println("ajax cgProductList getCafe->"+chanJoin.getCafe());
//		System.out.println("ajax cgProductList getStore->"+chanJoin.getStore());
//		System.out.println("ajax cgProductList getFitness->"+chanJoin.getFitness());
//		System.out.println("ajax cgProductList getcapacity->"+chanJoin.getR_capacity());
//		System.out.println("ajax cgProductList minPrice->"+chanJoin.getMin_price_r2());

		
		// userDetail에 있는 정보를 넣을 필드를 초기화 함.
		// user_id 초기화
		String user_id = "";
		// 권한 초기화.
		Collection<? extends GrantedAuthority> auth = null;
		
		// 만약에 로그인을 안했다면.
		if(userDetail == null) {
			user_id = "";
			auth = null;
		// 로그인을 했다면 필드에 넣어주기 .
		}else {
			user_id = userDetail.getUsername();
			auth = userDetail.getAuthorities();
		}
//		System.out.println("로그인하고 안하고 확인하기 -> "+user_id);
//		System.out.println("로그인하고 안하고 확인하기 -> "+auth);
		// id를 세팅하는 이유는 서비스에 가서 로그인한 아이디 값으로 zzim 목록을 가져오기 위함
		// 그리고 zzim 리스트의 user_id와 로그인한 아이디 값이 일치했을 때 accom모델에 있는 zzim_status에 Y라는 값을 넣기 위함. 서비스 딴 참고.
		chanJoin.setUser_id(user_id);
		
		List<ChanJoin> cgAjaxProductLists = service.cgGetAjaxProductListsByAccomtypeAndAddr(chanJoin);
		
		
//		System.out.println("ajax cgProductList  사이즈 -> "+cgAjaxProductLists.size());
//		System.out.println("ajax cgProductList  끝");
		return cgAjaxProductLists;
	}
	

}
