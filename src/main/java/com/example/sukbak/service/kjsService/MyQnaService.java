package com.example.sukbak.service.kjsService;

import java.util.List;

import com.example.sukbak.model.Qna;

public interface MyQnaService {

	List<Qna> getMyQnaAjaxList(String user_id, String qna_type);

	void myQnaUpdate(Qna qna);
	
	void myQnaDelete(int qna_id);


}
