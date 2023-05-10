package com.example.S20230403.dao.lysDao;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Event;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

public interface NotiQnaDao {

	List<Notice_Faq> listnofiFaq(Notice_Faq nf);

	List<Notice_Faq> listnc(Notice_Faq nf);

	List<Notice_Faq> listnt(Notice_Faq nf);

	int qnaInsert(Qna qna);

	List<Event> getEvent(Notice_Faq nf);

}
