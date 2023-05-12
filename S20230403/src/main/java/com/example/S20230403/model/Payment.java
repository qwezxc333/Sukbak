package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;

@Data
public class Payment {
	private int      pay_id;
	private String biz_id;
	private int      r_id;
	private String user_id;
	private Date   pay_date;
	private int      pay_amt;
	private String pay_method;
	private String pay_status;


	// 조인용
	private String biz_name;
	private String resv_name;
	private String phone;
	private int      r_price;
	private String r_name;
	private String check_in;
	private String check_out;

	//결제 API
	private String imp_uid;

}
