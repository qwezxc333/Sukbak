package com.example.sukbak.service.kjsService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sukbak.dao.kjsDao.ChatBotDao;
import com.example.sukbak.model.Notice_Faq;
import com.example.sukbak.model.Qna;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotServiceImpl implements ChatBotService {
	private final ChatBotDao cd;
	
	@Override
	public List<Notice_Faq> getNoticeFaqTitleList(String notice_type) {
    	List<Notice_Faq> noticeFaqTitle = cd.fetchNoticeFaqTitleList(notice_type);
    	
    	return noticeFaqTitle;
	}

	@Override
	public List<Notice_Faq> getNoticeFaqContentList(String notice_id) {
		List<Notice_Faq> noticeFaqContent = cd.fetchNoticeFaqContentList(notice_id);
		
    	return noticeFaqContent;
	}

	@Override
	public void qnaInsert(Qna qna) {
		
		cd.qnaInsert(qna);
	}

}
