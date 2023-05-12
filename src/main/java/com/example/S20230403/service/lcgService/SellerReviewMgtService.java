package com.example.S20230403.service.lcgService;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Review;

public interface SellerReviewMgtService {

	List<Review> getMyReviews(Review review);

	List<Accom> getMyAccoms(String sellerUser_id);

	int updateReviewDelRequestByPayId(Review review);

	Review getMyReviewConut(String biz_id);

	// ajax 
	List<Review> cgGetAjaxSortingReviewLists(Review review);
	

	




}
