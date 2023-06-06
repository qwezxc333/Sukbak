package com.example.sukbak.dao.kjsDao;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.JinJoin;
import com.example.sukbak.model.Review;
import com.example.sukbak.model.Review_Img;
import com.example.sukbak.model.Room_Img;

public interface AccomDetailDao {

	List<JinJoin> fetchAccomDetailRoomList(String biz_id, String checkIn, String checkOut);
	
	Accom fetchAccomBasicInfo(String biz_id);

	List<Room_Img> fetchRoomImgList(String biz_id);

	List<Review> fetchAccomReviewList(String biz_id);

	List<Review_Img> fetchAccomReviewImgList(List<Integer> payIds);

	Review fetchCalculateReview(String biz_id);



}
