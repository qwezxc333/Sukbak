package com.example.sukbak.dao.kjsDao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.sukbak.model.Notice_Faq;
import com.example.sukbak.model.Qna;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatBotDaoImpl implements ChatBotDao {
	private final SqlSessionTemplate session;

	@Override
	public List<Notice_Faq> fetchNoticeFaqTitleList(String notice_type) {
		List<Notice_Faq> noticeFaqTitle = session.selectList("jsNoticeFaqTitleList", notice_type);
		
		return noticeFaqTitle;
	}

	@Override
	public List<Notice_Faq> fetchNoticeFaqContentList(String notice_id) {
		List<Notice_Faq> noticeFaqContent = session.selectList("jsNoticeFaqContentList", notice_id);
		
		return noticeFaqContent;
	}

	@Override
	public void qnaInsert(Qna qna) {
		
		session.insert("jsQnaInsert", qna);
	}

}
