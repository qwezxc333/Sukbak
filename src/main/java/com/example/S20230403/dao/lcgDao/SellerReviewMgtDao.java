package com.example.S20230403.dao.lcgDao;

import java.util.List;

import org.apache.catalina.User;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Users;

public interface SellerReviewMgtDao {

	List<Accom> getMyInfo(String biz_id);

	List<Review> getMyReviews(Review review);

	Users getUserNicknames(String user_id);

	List<Review_Img> getReviewImgs(int pay_id);
	// 숙박가져오기
	List<Accom> getMyAccoms(String sellerUser_id);

	List<Room_Img> getMyAccomsImg();

	int updateReviewDelRequestByPayId(Review review);

	Review getMyReviewConut(String biz_id);

	List<Review> cgGetAjaxSortingReviewLists(Review review);







}
