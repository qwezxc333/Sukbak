package com.example.S20230403.controller.lcgController;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Review;
import com.example.S20230403.service.lcgService.SellerReviewMgtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SellerReivewMgtRestController {
	private final SellerReviewMgtService service;
	
	// biz_id와 kind(높은평점순 낮은평점순 인기순 구분자)를 파라미터로 사용해서 sorting해주는 로직
	@GetMapping("/cgGetAjaxSortingReviewLists")
	public List<Review> cgGetAjaxSortingReviewLists(Review review){
		//System.out.println("/cgGetAjaxSortingReviewLists kind-> "+review.getKind());
		//System.out.println("/cgGetAjaxSortingReviewLists kind-> "+review.getBiz_id());
		// kind가 잘 들어오는지 확인용.
//		if(1 == review.getKind()) {
//			System.out.println("최신순");
//		}else if(2 == review.getKind()) {
//			System.out.println("낮은 평점순");
//		}else {
//			System.out.println("높은 평점순");
//		}
		List<Review> ajaxReviewSortingLists = service.cgGetAjaxSortingReviewLists(review);
		return ajaxReviewSortingLists;
		
	}
}
