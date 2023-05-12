package com.example.S20230403.dao.kjsDao;

import java.util.List;
import java.util.Map;

import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Qna;

public interface ChatBotDao {

	List<Notice_Faq> fetchNoticeFaqTitleList(String notice_type);

	List<Notice_Faq> fetchNoticeFaqContentList(String notice_id);

	void qnaInsert(Qna qna);
	
}
