package com.example.S20230403.service.jjhService;

import java.util.List;

import com.example.S20230403.model.JooJoin;

public interface AdminPageService {
// 회원 비활성화
	int 			delUsers(JooJoin jooJoin);
	int 			delAccom(JooJoin jooJoin);
// 게시판 / 리뷰 삭제
	int				delQnA(int qna_id);
	int 			delQnARe(int qna_id);
//	int				delReview(int pay_id);
	int 			delReviewImg(int pay_id);
	int 			rejectDelReview(JooJoin jooJoin);
// Count for Paging
	int userTotal();
	int accomTotal();
	int qnaTotal();
	int reviewTotal();
// 리스트 불러오기
	List<JooJoin> userlist(JooJoin jooJoin);
	List<JooJoin> userlist(String user_id);
	List<JooJoin> accomlist(JooJoin jooJoin);
	List<JooJoin> qnalist(JooJoin jooJoin);
	List<JooJoin> reviewlist(JooJoin jooJoin);
// QnA 답변 달기
	JooJoin detailQna(int qna_id);
	int saveReply(JooJoin jooJoin);
//	게시판 / 리뷰 탭 나누기
	int qnaReTotal();
	List<JooJoin> qnaRelist(JooJoin jooJoin);
	int reviewDelTotal();
	List<JooJoin> reviewDellist(JooJoin jooJoin);
//	일반 회원 검색
	int conditionUserTotal(JooJoin jooJoin);
	List<JooJoin> listSearchUser(JooJoin jooJoin);
}
