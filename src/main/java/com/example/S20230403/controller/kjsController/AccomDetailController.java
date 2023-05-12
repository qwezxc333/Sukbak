package com.example.S20230403.controller.kjsController;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.JinJoin;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.service.kjsService.AccomDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccomDetailController {
	private final AccomDetailService as;
	
	// 주소값 2개를 처리 할 수 있도록 매핑 
	@GetMapping({"/accomDetail","/*/accomDetail"})
	public String accomDetail(String biz_id, Model model) {
		
		//메인숙소명, 메인주소, 기본정보
		Accom accomBasicInfo = as.getAccomBasicInfo(biz_id);
		//메인사진 만 가져오는 용도
		List<Room_Img> roomImg = as.getRoomImgList(biz_id);
		
		
		//리뷰리스트 가져오는용도(accomReview에 biz_id로 가져온 pay_id를 payIds List에 담아서 사진가져오는 메소드에 뿌려준다
		//그 payIds를 이용해서 Review.pay_id와 Review_Img.pay_id 를 매칭시킬 수 있다.
		List<Review> accomReview = as.getAccomReviewWithImagesList(biz_id);
		
		//한 사업자의 리뷰 총 갯수${accomReviewTotalCnt}
		Review calculateReview = as.getCalculateReview(biz_id);
		
		model.addAttribute("accomBasicInfo",accomBasicInfo);
		model.addAttribute("roomImg",roomImg);
		model.addAttribute("accomReview", accomReview);
		model.addAttribute("calculateReview", calculateReview);
		
		return "views/accomDetail";
	}
	
	@ResponseBody
	@GetMapping("/accomDetailRoomList")
	public List<JinJoin> accomDetailRoomList(String biz_id, String checkIn, String checkOut) {
		
		//1. 객실안내/예약 리스트에 날짜예약 체크해서 리스트 다 가져오되, 날짜에 걸린 객실만 is_reserved 로 1 받아 view에서 예약불가버튼 변경예정
		//2. 서비스단에서 금액*박수 구해서 총 계산한 값 JinJoin의 totalRoomPrice에 담았음
		List<JinJoin> accomDetailRoomList = as.getAccomDetailRoomList(biz_id, checkIn, checkOut);
		
		return accomDetailRoomList;
	}
	
}
