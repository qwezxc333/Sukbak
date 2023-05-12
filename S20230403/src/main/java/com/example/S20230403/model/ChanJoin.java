package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;

@Data
public class ChanJoin {
	
	// user
	private String user_id;
	private String name;
	private String autho_level;
	
	
	// accom
	private String biz_name;
	private String biz_id;
	private String accom_avail;
	private String accom_type;

	private String addr;
	private String pool;
	private String parking;
	private String cafe;
	private String restaurant;
	private String store;
	private String sauna;
	private String laundry;
	private String fitness;
	// 조회용
	
	
	// room
	//private String biz_id;
	private int    r_id;
	private String r_name;
	private int    r_price;
	private int    r_capacity;
	private int    bath_count;
	private String bath;
	private String wifi;
	private String ac;
	private String tv;
	private String bed_type;
	
	// room_img

	private int r_img_id;
	private String r_img;

	// 추가 
	
	//RESERVATION

   private String check_in;
   private String check_out;

   //PAYMENT
   private int pay_id;

   //REVIEW


   // 검색용
   private String min_price_r2; // 최저가격
   private String avg_rating;// 평균별점
   private int 	    kind;      // 인기순, 높은가격, 낮은가격순
   private String bed_type_r2;
   private String zzim_status;
}
