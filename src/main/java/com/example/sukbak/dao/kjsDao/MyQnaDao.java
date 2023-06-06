package com.example.sukbak.dao.kjsDao;

import java.util.List;

import com.example.sukbak.model.Qna;

public interface MyQnaDao {

	List<Qna> fetchMyQnaAjaxList(String user_id, String qna_type);

	void myQnaUpdate(Qna qna);
	
	void myQnaDelete(int qna_id);



}
