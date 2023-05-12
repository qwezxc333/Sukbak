package com.example.S20230403.model;

import lombok.Data;

@Data
public class JinJoin {
	   //ACCOM
	   private String biz_id;
	   
	   //ROOM
	   private int r_id;
	   private String r_name;
	   private int r_price;
	   
	   //ROOM_IMG
	   private String r_img;
	   
	   //객실 가격 * 박  구해서 총가격 담는 필드
	   private int totalRoomPrice;
	   
	   //예약->예약버튼 바꾸려고 담는 필드
	   private int is_reserved;
	   
	   
}
