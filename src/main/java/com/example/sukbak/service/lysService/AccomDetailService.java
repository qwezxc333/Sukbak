package com.example.sukbak.service.lysService;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.JinJoin;
import com.example.sukbak.model.Review;
import com.example.sukbak.model.Room_Img;

public interface AccomDetailService {

	List<Accom> getAccomList();

	List<JinJoin> getAccomDetailList(String biz_id, String checkIn, String checkOut);

	Accom getAccomBasicInfo(String biz_id);

	List<Room_Img> getRoomImgList(String biz_id);

	List<Review> getAccomReviewWithImagesList(String biz_id);

	Review getCalculateReview(String biz_id);




}
