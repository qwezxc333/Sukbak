package com.example.S20230403.service.kjsService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.S20230403.dao.kjsDao.MyQnaDao;
import com.example.S20230403.model.Qna;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyQnaServiceImpl implements MyQnaService {
	private final MyQnaDao md;
	
	// 문의 ajax
	@Override
	public List<Qna> getMyQnaAjaxList(String user_id, String qna_type) {
		List<Qna> myQnaAjax = md.fetchMyQnaAjaxList(user_id, qna_type);
		
		return myQnaAjax;
	}
	
	// 문의 수정
	@Override
	public void myQnaUpdate(Qna qna) {
		md.myQnaUpdate(qna);
		
	}
	
	// 문의 삭제
	@Override
	public void myQnaDelete(int qna_id) {
		md.myQnaDelete(qna_id);
	}

	


}
