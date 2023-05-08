package com.example.S20230403.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Review {
	private int pay_id;
	private String room_used;
	private int rating;
	private Date review_date;
	private String review_content;
	private String del_request;
	// 컬럼 추가
	private String del_reason;
	private String del_date;
	
	//USERS 진선, 찬규
	private String nickname;
	
	//PAYMENT 진선, 찬규
	private String biz_id;
	private String r_id;
	private String user_id;
	
	//ROOM 진선
	private String r_name;
	
	//이미지 목록 저장하는 필드 진선, 찬규
	private List<Review_Img> reviewImages;
	
	//리뷰 총갯수 진선
	private int totalReview;
	
	//리뷰 평점 진선
	private double avgReview;
	
	//찬규
	private String avg_rating;
	private String biz_name;
	private int    kind; // ajax sorting용
	
	// 건화 리뷰 페이지 조회용
	private Date	check_in, check_out;
	private int	pay_amt;
	private String	addr;
	private List<Review_Img> matchingReviewImages;
	private String review_img;
	
}