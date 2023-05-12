package com.example.S20230403.controller.lcgController;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Accom;
import com.example.S20230403.service.lcgService.ProductsFilterService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductsFilterController {
	private final ProductsFilterService service;
	

	// 1.accom_type별로 숙소 리스트를 가져오기 위한 로직
	@GetMapping("/cgProductLists")
	public String cgGetProductByAccomtype(@AuthenticationPrincipal PrincipalDetail userDetail,
										  @RequestParam("accom-type") String accom_type, 
										  Accom accom, 
										  Model model) {
//		System.out.println("리펙토링 cgProductByAccomtype 시작");
//		System.out.println("리펙토링 cgProductByAccomtype accom_type->"+accom_type);
		
		// userDetail에 있는 정보를 넣을 필드를 초기화 함.
		// user_id 초기화
		String user_id = "";
		// 권한 초기화.
		Collection<? extends GrantedAuthority> auth = null;
		
		// 만약에 로그인을 안했다면 없는상태로 
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
		
		// accom 모델에 넣어주기 
		accom.setAccom_type(accom_type);
		
		// id를 세팅하는 이유는 서비스에 가서 로그인한 아이디 값으로 zzim 목록을 가져오기 위함
		// 그리고 zzim 리스트의 user_id와 로그인한 아이디 값이 일치했을 때 accom모델에 있는 zzim_status에 Y라는 값을 넣기 위함. 서비스 딴 참고.
		accom.setUser_id(user_id);
		List<Accom> cgProductLists = service.cgGetProductByAccomtype(accom);
		
		// accom_type이 호텔들이면 호텔페이지로 넘어가게 했음.
		// 페이지를 나누는 이유는 ajax 작동방식이 다르고 필터바 내용도 다르기 때문
		// accom_type이 호텔이라면 
		if(accom_type.equals("511,513,514,515")) {
			model.addAttribute("user_id", user_id);
			// auth를 보내는 이유는 user권한을 가지고 있는 사람들만 찜을 할 수 있게 하기 위함.
			model.addAttribute("auth", auth);
			model.addAttribute("products", cgProductLists);
			model.addAttribute("accom_type", accom_type);
			return "/views/productLists/cgProductHotelLists";
			// accom type 이 모텔 게하 팬션이라면.
		} else {
			model.addAttribute("user_id", user_id);
			// auth를 보내는 이유는 user권한을 가지고 있는 사람들만 찜을 할 수 있게 하기 위함.
			model.addAttribute("auth", auth);
			model.addAttribute("products", cgProductLists);
			model.addAttribute("accom_type", accom_type);		
			//System.out.println("컨트롤러 /cgProduct/motel 끝");
			return "/views/productLists/cgProductListsWithoutHotel";
		}
		
	}
		
	
	// 모텔, 게하, 펜션  지역별로 리스트 뽑아오기
	@GetMapping("cgProductListsByAddr")
	public String cgGetProductListsByAddr(@AuthenticationPrincipal PrincipalDetail userDetail,
										  @RequestParam("accom-type") String accom_type,
									   	  @RequestParam("addr") String addr,
									   	  Accom accom,
									   	  Model model) {
		//System.out.println("컨트롤러 cgProductListsByAddr 시작");
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
		accom.setUser_id(user_id);
		accom.setAccom_type(accom_type);
		accom.setAddr(addr);
		
		List<Accom> cgProductListsByAddr = service.cgGetProductListsByAddr(accom);
		
//		System.out.println("컨트롤러 cgProductListsByAddr getAccom_type, addr-> "+accom.getAccom_type() +"," + accom.getAddr());
//		System.out.println("컨트롤러 cgProductListsByAddr 사이즈 -> "+cgProductListsByAddr.size());
		
		// accom_type이 호텔들이면 호텔페이지로 넘어가게 했음.
		// 페이지를 나누는 이유는 ajax 작동방식이 다르고 필터바 내용도 다르기 때문
		if(accom_type.equals("511,513,514,515")) {
			model.addAttribute("user_id", user_id);
			// auth를 보내는 이유는 user권한을 가지고 있는 사람들만 찜을 할 수 있게 하기 위함.
			model.addAttribute("auth", auth);
			model.addAttribute("products", cgProductListsByAddr);
			model.addAttribute("accom_type", accom_type);
			model.addAttribute("addr", addr);
			return "/views/productLists/cgProductHotelListsByAddr";
		} else {
			model.addAttribute("user_id", user_id);
			// auth를 보내는 이유는 user권한을 가지고 있는 사람들만 찜을 할 수 있게 하기 위함.
			model.addAttribute("auth", auth);
			model.addAttribute("products", cgProductListsByAddr);
			model.addAttribute("accom_type", accom_type);
			model.addAttribute("addr", addr);
			return "/views/productLists/cgProductListsWithoutHotelByAddr";
		}
	}

}
