package com.example.S20230403.controller.lcgController;

import java.util.List;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Review;
import com.example.S20230403.service.lcgService.SellerReviewMgtService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SellerReviewMgtController {
	private final SellerReviewMgtService service;
	
	// 나의 업체를 가져오는 로직
	@GetMapping("/biz/review")
	public String getMyAccoms(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		//System.out.println("컨트롤러 getMyAccoms 시작");
		String sellerUser_id = userDetail.getUsername();

		//System.out.println("컨트롤러 getMyAccoms  selleruser_id-> "+sellerUser_id);
		List<Accom> myAccoms = service.getMyAccoms(sellerUser_id);
		//System.out.println("컨트롤러 getMyAccoms myAccoms 사이즈 -> "+myAccoms.size());
		
		model.addAttribute("myAccoms", myAccoms);
		return "/views/sellerReview/sellerReviewMgt";
	}
	
	// 실제 내 업소에 달린 리뷰를 가져오는 로직
	@RequestMapping("/biz/reviewDetail")
	public String getMyReviews(@RequestParam("biz_id") String biz_id, Model model, Review review) {
		// ajax사용 시에 사용한 kind 값을 유지하기 위해 Review객체 안에 kind가 있음
		//System.out.println("컨트롤러 getMyReviews 시작");
		//System.out.println("컨트롤러 getMyReviews biz_id-> "+biz_id);
		review.setBiz_id(biz_id);
		// 리뷰 다가져오기
		List<Review> myReviews = service.getMyReviews(review);
		//System.out.println("컨트롤러 getMyReviews  myReviews-> "+myReviews);
		
		// biz_id별로  리뷰 개수 가져오기, biz_id도 가져와야됨. ajax사용해야됨.
		Review totalReviewAndBiz_id = service.getMyReviewConut(biz_id);
		//System.out.println("컨트롤러 내 리뷰 개수 -> "+totalReviewAndBiz_id);
		if(totalReviewAndBiz_id == null) {
			totalReviewAndBiz_id = new Review();
			totalReviewAndBiz_id.setTotalReview(0);
		}
	
		model.addAttribute("myReviews", myReviews);
		model.addAttribute("totalReviewAndBiz_id", totalReviewAndBiz_id);
		return "/views/sellerReview/reviewDetail";
		
	}
	
	// 부당하게 달린 리뷰를 삭제요청하는 로직
	@PostMapping("/biz/reviewDeleteRequest")
	public String reviewDeleteRequest(Review review, Model model) {
//		System.out.println("updateReviewDelRequestByPayId 시작");
//		System.out.println("delReason-> "+review.getDel_reason());
//		System.out.println("review payid-> "+review.getPay_id());
//		System.out.println("review payid-> "+review.getBiz_id());
		String biz_id = review.getBiz_id();
		
		int resultRequest = service.updateReviewDelRequestByPayId(review);
//		System.out.println("업데이트 결과 1이 나와야됨 1 -> "+resultRequest);
//		System.out.println("업데이트 결과 biz_id이 나와야됨  biz_id-> "+biz_id);
		
		// biz_id를 보내는 이유
		// @RequestParam("biz_id") String biz_id가 있기 때문에 받아줘야 한다. 
		model.addAttribute("biz_id", biz_id);
		return "forward:/biz/reviewDetail";
	}

	
}
