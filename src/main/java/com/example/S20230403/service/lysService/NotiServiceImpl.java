package com.example.S20230403.service.lysService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.S20230403.dao.lysDao.NotiQnaDao;
import com.example.S20230403.model.Event;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Qna;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotiServiceImpl implements NotiService {
	private final NotiQnaDao notiQnaDao;

	@Override
	public List<Notice_Faq> listNoticeFaq(Notice_Faq nf) {
		List<Notice_Faq> nfqlist =notiQnaDao.listnofiFaq(nf);

		return nfqlist;
	}

	@Override
	public List<Notice_Faq> listnc(Notice_Faq nf) {
		List<Notice_Faq> listnc =notiQnaDao.listnc(nf);

		return listnc;
	}

	@Override
	public List<Notice_Faq> listnt(Notice_Faq nf) {
		List<Notice_Faq> listnt =notiQnaDao.listnt(nf);

		return listnt;
	}

	public int qnaInset(Qna qna) {
		int qnaInset=notiQnaDao.qnaInsert(qna);
		return qnaInset;
	}

	public List<Event> getEvent(Notice_Faq nf) {
		List<Event> sukbakEvents = notiQnaDao.getEvent(nf);
		return sukbakEvents;
	}

	
}
