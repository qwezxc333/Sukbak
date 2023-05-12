package com.example.S20230403.service.lysService;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

public interface NotiService {

	List<Notice_Faq> listNoticeFaq(Notice_Faq nf);

	List<Notice_Faq> listnc(Notice_Faq nf);

	List<Notice_Faq> listnt(Notice_Faq nf);

	int qnaInset(Qna qna);



}
