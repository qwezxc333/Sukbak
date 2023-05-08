package com.example.S20230403.controller.lcgController;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Review;
import com.example.S20230403.service.lcgService.SellerReviewMgtService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SellerReviewMgtController {
	private final SellerReviewMgtService service;
	
	@GetMapping("/biz/review")
	public String getMyAccoms(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("컨트롤러 getMyAccoms 시작");
		String sellerUser_id = userDetail.getUsername();

		System.out.println("컨트롤러 getMyAccoms  selleruser_id-> "+sellerUser_id);
		List<Accom> myAccoms = service.getMyAccoms(sellerUser_id);
		System.out.println("컨트롤러 getMyAccoms myAccoms 사이즈 -> "+myAccoms.size());
		
		model.addAttribute("myAccoms", myAccoms);
		model.addAttribute("user_id", sellerUser_id);
		return "/views/sellerReview/sellerReviewMgt";
	}
	
	@RequestMapping("/biz/reviewDetail")
	public String getMyReviews(@RequestParam("biz_id") String biz_id, Model model) {
		
		System.out.println("컨트롤러 getMyReviews 시작");
		System.out.println("컨트롤러 getMyReviews biz_id-> "+biz_id);
		// 리뷰 다가져오기
		List<Review> myReviews = service.getMyReviews(biz_id);
		System.out.println("컨트롤러 getMyReviews  myReviews-> "+myReviews);
		
		// biz_id별로  리뷰 개수 가져오기, biz_id도 가져와야됨. ajax사용해야됨.
		Review totalReviewAndBiz_id = service.getMyReviewConut(biz_id);
		System.out.println("컨트롤러 내 리뷰 개수 -> "+totalReviewAndBiz_id);
	
		model.addAttribute("myReviews", myReviews);
		//model.addAttribute("user_id", sellerUser_id);
		model.addAttribute("totalReviewAndBiz_id", totalReviewAndBiz_id);
		return "/views/sellerReview/reviewDetail";
		
	}
	
	@PostMapping("/biz/reviewDeleteRequest")
	public String reviewDeleteRequest(Review review, Model model) {
		System.out.println("updateReviewDelRequestByPayId 시작");
		System.out.println("delReason-> "+review.getDel_reason());
		System.out.println("review payid-> "+review.getPay_id());
		System.out.println("review payid-> "+review.getBiz_id());
		String biz_id = review.getBiz_id();
		
		int resultRequest = service.updateReviewDelRequestByPayId(review);
		System.out.println("업데이트 결과 1이 나와야됨 1 -> "+resultRequest);
		System.out.println("업데이트 결과 biz_id이 나와야됨  biz_id-> "+biz_id);
		if(resultRequest == 1) {
			model.addAttribute("biz_id", biz_id);
			return "forward:/biz/reviewDetail";
		}else
		model.addAttribute("biz_id", biz_id);
		return "forward:/biz/reviewDetail";
	}

	
}
