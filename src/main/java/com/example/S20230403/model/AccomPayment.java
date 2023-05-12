package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;
@Data
public class AccomPayment {

	private String biz_id;
	private String user_id;
	private String availability;
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
	

	//조회용
	private String search;
	private String keyword;
	
	
	private int      pay_id;
	private int      r_id;
	private Date   pay_date;
	private String pay_method;
	private String pay_status;


	// 조인용
	private String resv_name;
	private String phone;
	private String resv_phone;
	private int      r_price;
	private String r_name;
	private String check_in;
	private String check_out;
	private int      pay_amt;
	private int      resv_capa;
	private Date   resv_date;
	private String sout_avail;
	private int      r_capacity;
	private int      RESV_CAPA;
	
}
