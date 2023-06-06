package com.example.sukbak.service.lcgService;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.Review;

public interface SellerReviewMgtService {

	List<Review> getMyReviews(Review review);

	List<Accom> getMyAccoms(String sellerUser_id);

	int updateReviewDelRequestByPayId(Review review);

	Review getMyReviewConut(String biz_id);

	// ajax 
	List<Review> cgGetAjaxSortingReviewLists(Review review);
	

	




}
