package com.example.S20230403.model;

import lombok.Data;

@Data
public class Room {
	private String biz_id;
	private int r_id;
	private String r_name;
	private int r_price;
	private int r_capacity;
	private String r_info;
	private int r_count;
	private int bath_count;
	private String bath;
	private String wifi;
	private String ac;
	private String tv;
	private String bed_type;

	// room_img - 재영
	private int r_img_id;
	private String r_img;

	// users - 재영
	private String user_id;

	// room에 붙여넣기
	// room_img - 재영
	// users - 재영
	private String biz_name;

}
