package com.example.S20230403.controller.lcgController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.model.Review;
import com.example.S20230403.service.lcgService.SellerReviewMgtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SellerReivewMgtRestController {
	private final SellerReviewMgtService service;
	
	@GetMapping("/cgGetAjaxSortingReviewLists")
	public List<Review> cgGetAjaxSortingReviewLists(Review review){
		System.out.println("/cgGetAjaxSortingReviewLists kind-> "+review.getKind());
		System.out.println("/cgGetAjaxSortingReviewLists kind-> "+review.getBiz_id());
		// kind가 잘 들어오는지 확인용.
		if(1 == review.getKind()) {
			System.out.println("최신순");
		}else if(2 == review.getKind()) {
			System.out.println("낮은 평점순");
		}else {
			System.out.println("높은 평점순");
		}
		List<Review> ajaxReviewSortingLists = service.cgGetAjaxSortingReviewLists(review);
		return ajaxReviewSortingLists;
		
	}
}
