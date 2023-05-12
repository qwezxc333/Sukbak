package com.example.S20230403.model;

import java.util.Date;

import lombok.Data;

@Data
public class Qna_Re {
	private int qna_id;
	private String user_id;
	private String reply;
	private Date qna_re_date;
}
