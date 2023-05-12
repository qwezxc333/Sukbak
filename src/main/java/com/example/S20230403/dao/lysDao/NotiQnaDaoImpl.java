package com.example.S20230403.dao.lysDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Event;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Repository
@RequiredArgsConstructor
@Slf4j
public class NotiQnaDaoImpl implements NotiQnaDao {
	private final SqlSession session;


	@Override
	public List<Notice_Faq> listnofiFaq(Notice_Faq nf) {
		List<Notice_Faq> nfqlist =null;
		try {
			nfqlist =session.selectList("ysNotifaq",nf);
			//system.out.println(" 다오 임플 노트"+nfqlist.size());
		} catch (Exception e) {
			//system.out.println("다오 임플 노트쪽 에러 노트쪽 "+e.getMessage());
		}
		return nfqlist;	
	}

	@Override
	public List<Notice_Faq> listnc(Notice_Faq nf) {
		List<Notice_Faq> listnc =null;
		try {
			listnc =session.selectList("yslistnc",nf);
			//system.out.println(" 다오 임플 노트"+listnc.size());
		} catch (Exception e) {
			//system.out.println("다오 임플 노트쪽 에러 노트쪽 "+e.getMessage());
		}
		return listnc;	

	}

	@Override
	public List<Notice_Faq> listnt(Notice_Faq nf) {
		List<Notice_Faq> listnt =null;
		try {
			listnt =session.selectList("yslistnt",nf);
			//system.out.println(" 다오 임플 노트"+listnt.size());
		} catch (Exception e) {
			//system.out.println("다오 임플 노트쪽 에러 노트쪽 "+e.getMessage());
		}
		return listnt;	
	}

	@Override
	public int qnaInsert(Qna qna) {
		int qnaInsert=0;
		try {
			qnaInsert =session.insert("ysQnaInsert",qna);
		} catch (Exception e) {
			//system.out.println("qnaInsert 에러쪽 "+e.getMessage());
		}		return qnaInsert;
	}

	@Override
	public List<Event> getEvent(Notice_Faq nf) {
		List<Event> sukbakEvents = null;
		try {
			sukbakEvents = session.selectList("getEvent");
			//system.out.println("dao getevents 사이즈 -> "+sukbakEvents.size());
		} catch (Exception e) {
			//system.out.println("dao getevents error -> "+e.getMessage());
			// TODO: handle exception
		}
		return sukbakEvents;
	}

		
}
