package com.example.S20230403.dao.kjsDao;

import java.util.List;

import com.example.S20230403.model.Qna;

public interface MyQnaDao {

	List<Qna> fetchMyQnaList(String user_id, String qna_type);

}
