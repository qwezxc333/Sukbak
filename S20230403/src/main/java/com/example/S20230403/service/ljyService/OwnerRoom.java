package com.example.S20230403.service.ljyService;

import com.example.S20230403.model.Room;

import lombok.Data;

@Data
public class OwnerRoom {
	
	public Room toRoom() {
		Room room = new Room();
		room.setBiz_id(this.biz_id);
		room.setR_id(this.r_id);
		room.setR_name(this.r_name);
		room.setR_price(this.r_price);
		room.setR_capacity(this.r_capacity);
		room.setR_info(this.r_info);
		room.setR_count(this.r_count);
		room.setBed_type(this.bed_type);
		room.setBath_count(this.bath_count);
		
		room.setR_img_id(this.r_img_id);
		room.setR_img(this.r_img);
		room.setUser_id(this.user_id);
		room.setBiz_name(this.biz_name);
		
		room.setBath(this.getStringBool(this.hasBath));
		room.setWifi(this.getStringBool(this.hasWifi));
		room.setAc(this.getStringBool(this.hasAc));
		room.setTv(this.getStringBool(this.hasTv));
		
		return room;
	}
	
	private String getStringBool(boolean isTrue) {
		return isTrue ? "Y" : "N";
				
	}
	
	private boolean hasBath = false;
	private boolean hasWifi = false;
	private boolean hasAc = false;
	private boolean hasTv = false;
	
	private String biz_id;
	private int r_id;
	private String r_name;
	private int r_price;
	private int r_capacity;
	private String r_info;
	private int r_count;
	private int bath_count;

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
