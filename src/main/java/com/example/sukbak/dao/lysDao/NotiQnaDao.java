package com.example.sukbak.dao.lysDao;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.AccomPayment;
import com.example.sukbak.model.Event;
import com.example.sukbak.model.Notice_Faq;
import com.example.sukbak.model.Payment;
import com.example.sukbak.model.Qna;
import com.example.sukbak.model.Room;
import com.example.sukbak.model.Users;

public interface NotiQnaDao {

	List<Notice_Faq> listnofiFaq(Notice_Faq nf);

	List<Notice_Faq> listnc(Notice_Faq nf);

	List<Notice_Faq> listnt(Notice_Faq nf);

	int qnaInsert(Qna qna);

	List<Event> getEvent(Notice_Faq nf);

}
