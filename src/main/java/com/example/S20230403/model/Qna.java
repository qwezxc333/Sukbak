package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;

@Data
public class Qna {
	private int qna_id;
	private String user_id;
	private String qna_type;
	private String qna_title;
	private String qna_content;
	private String qna_date;
	
	//진선
	private String reply;
	private Date qna_re_date;
	
}
