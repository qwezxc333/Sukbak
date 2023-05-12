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
	   private String accom_avail;
	   private String biz_name;
	   private String addr;
	   
	   // ROOM
	   private int r_id;
	   private String r_name;
	   private int r_price;
//	   private int r_capacity;
	   
	   // ROOMIMG
	   private int r_img_id;
	   private String r_img;
	   
	   // RESERVATION
	   private int resv_capa;
	   private Date check_in;
	   private Date check_out;
//	   private String resv_name;
//	   private String resv_phone;
	   private int resv_id;
	   
	   // PAYMENT
	   private int pay_id;
//	   private Date pay_date;
//	   private String pay_method;
	   private String pay_status;
	   private int pay_amt;
	   
	   // REVIEW
	   private String room_used;
	   private int rating;
	   private Date review_date;
	   private String review_content;
	   
	   // REVIEWIMG
	   private int review_img_id;
	   private String review_img;
	   
	   // OUT
	   private String out_price;
	   private String out_uncom;
	   private String out_nouse;
	   private String out_secure;
	   private String out_content;
	   
	   // 조회용
	   private String resv_date;
	   private int payment_id;
	   private int review_pay_id;
}
