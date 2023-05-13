package com.example.S20230403.dao.kjsDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Qna;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyQnaDaoImpl implements MyQnaDao {
	private final SqlSessionTemplate session;
	
	// 문의 ajax
	@Override
	public List<Qna> fetchMyQnaAjaxList(String user_id, String qna_type) {
		
		Map<String, Object> myQnaCheck = new HashMap<>();
		myQnaCheck.put("user_id", user_id);
		myQnaCheck.put("qna_type", qna_type);
		
		List<Qna> myQnaAjax = session.selectList("jsMyQnaAjaxList", myQnaCheck);
		
		return myQnaAjax;
	}
	
	// 문의 수정
	@Override
	public void myQnaUpdate(Qna qna) {
		session.update("jsMyQnaUpdate", qna);
	}
	
	// 문의 삭제
	@Override
	public void myQnaDelete(int qna_id) {
		session.delete("jsMyQnaDelete", qna_id);
	}

	


}
