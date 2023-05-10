package com.example.S20230403.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Event {
	private int event_id;
	private String event_title;
	private String event_content;
	private String event_img;
	private Date event_start_date;
	private Date event_end_date;
}
