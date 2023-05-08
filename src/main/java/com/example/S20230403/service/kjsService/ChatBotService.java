package com.example.S20230403.service.kjsService;

import java.util.List;
import java.util.Map;

import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Qna;

public interface ChatBotService {

	List<Notice_Faq> getNoticeFaqTitleList(String notice_type);

	List<Notice_Faq> getNoticeFaqContentList(String notice_id);

	void qnaInsert(Qna qna);

}
