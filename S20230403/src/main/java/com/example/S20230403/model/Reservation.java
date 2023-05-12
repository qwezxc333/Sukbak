package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;

@Data
public class Reservation {
	private int RESV_ID;
	private String biz_id;
	private int r_id;
	private String user_id;
	private int resv_capa;
	private String check_in;
	private String check_out;
	private String resv_name;
	private String resv_phone;
}
