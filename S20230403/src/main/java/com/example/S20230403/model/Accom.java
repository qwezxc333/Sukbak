package com.example.S20230403.model;

import java.util.List;

import lombok.Data;

@Data
public class Accom {
	private String biz_id;
	private String user_id;
	private String accom_avail;
	private String accom_type;
	private String biz_name;
	private String addr;
	private String tel;
	private int      room_count;
	private String description;
	private String latitude;
	private String longitude;
	private String pool;
	private String parking;
	private String cafe;
	private String restaurant;
	private String store;
	private String sauna;
	private String laundry;
	private String fitness;
	
	// 재영
	private String content;
	private String user_status;
	
	// 찬규
	private String min_price_r2;
	private String r_img;
	private String avg_rating;
	private String r_name;
	private String r_id;
	private List<String> accom_types;
	private String zzim_status;
	
	//조회용
	private String search;
	private String keyword;

}
