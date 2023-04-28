package com.example.S20230403.service.jjhService;

import java.util.List;

import com.example.S20230403.model.JooJoin;

public interface AdminPageService {
	int 			delUsers(JooJoin jooJoin);
	int 			delAccom(JooJoin jooJoin);
	int				delQnA(int qna_id);
	int delQnARe(int qna_id);
	int				delReview(int pay_id);
//	관리자 페이지 페이징
	int userTotal();
	int accomTotal();
	int qnaTotal();
	int reviewTotal();
	List<JooJoin> userlist(JooJoin jooJoin);
	List<JooJoin> accomlist(JooJoin jooJoin);
	List<JooJoin> qnalist(JooJoin jooJoin);
	List<JooJoin> reviewlist(JooJoin jooJoin);
//	review 답글
	JooJoin detailQna(int qna_id);
	int saveReply(JooJoin jooJoin);
}
