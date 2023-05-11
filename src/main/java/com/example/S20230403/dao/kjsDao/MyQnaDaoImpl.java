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
	
	@Override
	public List<Qna> fetchMyQnaList(String user_id, String qna_type) {
		
		Map<String, Object> myQnaCheck = new HashMap<>();
		myQnaCheck.put("user_id", user_id);
		myQnaCheck.put("qna_type", qna_type);
		
		List<Qna> myQna = session.selectList("jsMyQnaList", myQnaCheck);
		
		return myQna;
	}

}
