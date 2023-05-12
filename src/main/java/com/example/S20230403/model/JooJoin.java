package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;

@Data
public class JooJoin {

	//	Users
	private String user_id;
	private String name;
	private String nickname;
	private String password;
	private String phone;
	private String telecom;
	private String gender;
	private String agree;
	private String user_status;
	private String auth_level;
	
//	Accom
	private String biz_id;
//	private String user_id;
	private String accom_avail;
	private String accom_type;
	private String biz_name;
	private String addr;
	private String tel;
	private int room_count;
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
	
//	QnA
	private int qna_id;
//	private String user_id;
	private String qna_type;
	private String qna_title;
	private String qna_content;
	private String qna_date;
	
//	QnARe
//	private int qna_id;
//	private String user_id;
	private String reply;
	private String qna_re_date;
	
//	Review
	private int pay_id;
	private String room_used;
	private int rating;
	private Date review_date;
	private String review_content;
	private String del_request;
	private String del_reason;
	private String del_date;
	
//	ReviewImg
//	private int pay_id;
	private int review_img_id;
	private String review_img;
	
//	Event
	private int event_id;
	private String event_title;
	private String event_content;
	private String event_img;
	
//	NoticeFaq
	private int notice_id;
	private String notice_type;
	private String notice_title;
	private String notice_content;
	
//	admin page 조회용
	private int rownum;
	private int rn;
	private String search;
	private String keyword;
	private String pageNum;
	private int start;
	private int end;
	private String s_user_id;
	private String s_name;
}
