package com.example.S20230403.service.lysService;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.JinJoin;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Room_Img;

public interface AccomDetailService {

	List<Accom> getAccomList();

	List<JinJoin> getAccomDetailList(String biz_id, String checkIn, String checkOut);

	Accom getAccomBasicInfo(String biz_id);

	List<Room_Img> getRoomImgList(String biz_id);

	List<Review> getAccomReviewWithImagesList(String biz_id);

	Review getCalculateReview(String biz_id);




}
