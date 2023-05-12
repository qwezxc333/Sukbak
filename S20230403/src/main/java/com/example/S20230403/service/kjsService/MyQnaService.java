package com.example.S20230403.service.kjsService;

import java.util.List;

import com.example.S20230403.model.Qna;

public interface MyQnaService {

	List<Qna> getMyQnaList(String user_id, String qna_type);

}
