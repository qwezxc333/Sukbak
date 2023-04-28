package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;

@Data
public class Reservation {
	private String biz_id;
	private int r_id;
	private String user_id;
	private int resv_capa;
	private Date check_in;
	private Date check_out;
	private String resv_name;
	private String resv_phone;
}
