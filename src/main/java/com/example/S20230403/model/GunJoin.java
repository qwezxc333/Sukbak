package com.example.S20230403.model;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class GunJoin {
	   // COMM
	   private String big_code;
	   private String mid_code;
	   private String content;
	   
	   // USERS
	   private String user_id;
	   private String name;
	   private String nickname;
	   private String password;
	   private String phone;
//	   private String telecom;
	   private String gender;
//	   private String agree;
	   private String user_status;
	   private String auth_level;
	   
	   // ACCOM
	   private String biz_id;
//	   private String user_id;
	   private String accom_avail;
//	   private String accom_type;
	   private String biz_name;
	   private String addr;
	   
	   // ROOM
//	   private String biz_id;
	   private int r_id;
	   private String r_name;
	   private int r_price;
//	   private int r_capacity;
	   
	   // ROOMIMG
//	   private String biz_id;
//	   private int r_id;
	   private int r_img_id;
	   private String r_img;
	   
	   // RESERVATION
//	   private String biz_id;
//	   private int r_id;
//	   private String user_id;
	   private int resv_capa;
	   private Date check_in;
	   private Date check_out;
//	   private String resv_name;
//	   private String resv_phone;
	   
	   // PAYMENT
	   private int pay_id;
//	   private String biz_id;
//	   private int r_id;
//	   private String user_id;
//	   private Date pay_date;
//	   private String pay_method;
	   private String pay_status;
	   private int pay_amt;
	   
	   // REVIEW
//	   private int pay_id;
	   private String room_used;
	   private int rating;
	   private Date review_date;
	   private String review_content;
	   
	   // REVIEWIMG
//	   private int pay_id;
	   private int review_img_id;
	   private String review_img;
	   
	   // OUT
//	   private String user_id;
	   private String out_price;
	   private String out_uncom;
	   private String out_nouse;
	   private String out_secure;
	   private String out_content;
	   
	   // 조회용
	   public String	login_id = "balldrea@histats.com";
	   public LocalDate	myViewDate = LocalDate.of(2023, 5, 15);
	   public String	usedBiz_id = "928-404-7612";
	   public int 		usedR_id = 3;
}
