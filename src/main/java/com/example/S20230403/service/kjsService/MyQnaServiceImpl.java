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
	
	@Override
	public List<Qna> getMyQnaList(String user_id, String qna_type) {

		List<Qna> myQna = md.fetchMyQnaList(user_id, qna_type);
		
		return myQna;
	}

}
