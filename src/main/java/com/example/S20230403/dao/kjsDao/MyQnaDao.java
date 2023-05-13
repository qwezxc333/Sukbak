package com.example.S20230403.dao.kjsDao;

import java.util.List;

import com.example.S20230403.model.Qna;

public interface MyQnaDao {

	List<Qna> fetchMyQnaAjaxList(String user_id, String qna_type);

	void myQnaUpdate(Qna qna);
	
	void myQnaDelete(int qna_id);



}
