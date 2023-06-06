package com.example.sukbak.service.lysService;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.AccomPayment;
import com.example.sukbak.model.Notice_Faq;
import com.example.sukbak.model.Payment;
import com.example.sukbak.model.Qna;
import com.example.sukbak.model.Room;
import com.example.sukbak.model.Users;

public interface NotiService {

	List<Notice_Faq> listNoticeFaq(Notice_Faq nf);

	List<Notice_Faq> listnc(Notice_Faq nf);

	List<Notice_Faq> listnt(Notice_Faq nf);

	int qnaInset(Qna qna);



}
