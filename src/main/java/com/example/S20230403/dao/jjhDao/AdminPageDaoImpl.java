package com.example.S20230403.dao.jjhDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.model.JooJoin;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class AdminPageDaoImpl implements AdminPageDao {
	private final SqlSession session;

//	Paging --------------------------------------------------------------

//	USER 전체 조회 및 페이징
	@Override
	public int userTotal() {
		// TODO Auto-generated method stub
		return session.selectOne("jhUserTotal");
	}
	
	@Override
	public List<JooJoin> userlist(String user_id) {
		List<JooJoin> userlist =null;
		try {
			userlist = session.selectList("jhUserlist", user_id);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl userlist Exception : "+e.getMessage());
		}
		return userlist;
	}
	@Override
	public List<JooJoin> userlist(JooJoin jooJoin) {
		List<JooJoin> userlist =null;
		try {
			userlist = session.selectList("jhUserlist", jooJoin);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl userlist Exception : "+e.getMessage());
		}
		return userlist;
	}

//	ACCOM 전체 조회 및 페이징
	@Override
	public int accomTotal() {
		// TODO Auto-generated method stub
		return session.selectOne("jhAccomTotal");
	}
	@Override
	public List<JooJoin> accomlist(JooJoin biz_id) {
		List<JooJoin> accomlist =null;
		try {
//			System.out.println("jjhDao AdminPageDaoImpl accomlist 시작");
			accomlist = session.selectList("jhAccomlist", biz_id);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl accomlist Exception : "+e.getMessage());
		}
		return accomlist;
	}
	
//	QNA 전체 조회 및 페이징
	@Override
	public int qnaTotal() {
		// TODO Auto-generated method stub
		return session.selectOne("jhQnaTotal");
	}
	@Override
	public List<JooJoin> qnalist(JooJoin qna_id) {
		List<JooJoin> qnalist =null;
		try {
			qnalist = session.selectList("jhQnalist", qna_id);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl qnalist Exception : "+e.getMessage());
		}
		return qnalist;
	}
	
//	REVIEW 전체 조회 및 페이징
	@Override
	public int reviewTotal() {
		// TODO Auto-generated method stub
		return session.selectOne("jhReviewTotal");
	}
	@Override
	public List<JooJoin> reviewlist(JooJoin review_id) {
		List<JooJoin> reviewlist =null;
		try {
			reviewlist = session.selectList("jhReviewlist", review_id);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl reviewlist Exception : "+e.getMessage());
		}
		return reviewlist;
	}
	

//	회원 비활성화-----------------------------------------------------
	
	@Override
	public int delUsers(JooJoin jooJoin) {
		return session.update("adminDeleteUser", jooJoin);
	}

	@Override
	public int delAccom(JooJoin jooJoin) {
		return session.update("adminDeleteAccom", jooJoin);
	}

//	각 row 삭제-----------------------------------------------------------
	
	@Override
	public int delQnARe(int qna_id) {
		return session.delete("adminDeleteQnARe", qna_id);
	}
	
	@Override
	public int delQnA(int qna_id) {
		int result = 0;
		result = session.delete("adminDeleteQnA", qna_id);
		return result;
	}
	
	@Override
	public int delReviewImg(int pay_id) {
		int result = 0;
//		System.out.println("다오 delReviewImg 삭제 시작");
		try {
			result = session.delete("adminDeleteReviewImg", pay_id);
//			System.out.println("다오 delReviewImg 삭제 시작 result -> "+result);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("다오 delReviewImg 삭제 에러-> "+e.getMessage());
		}
		return result;
	}
	
	@Override
	public int delReview(int pay_id) {
		return session.delete("adminDeleteReview", pay_id);
	}
	
//	-------------------------------------------------------
	
//	Review Reply
	@Override
	public JooJoin detailQna(int qna_id) {
		JooJoin jooJoin = new JooJoin();
		try {
			jooJoin = session.selectOne("jhQnaSelOne", qna_id);
//			System.out.println("jjhDao AdminPageDaoImpl listReply qna_id-> "+qna_id);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl listReply Exception-> "+e.getMessage());
		}
		return jooJoin;
	}
	@Override
	public int saveReply(JooJoin jooJoin) {
//		System.out.println("jjhDao AdminPageDaoImpl insertReply start");
		return session.insert("jhInsertQnaRe", jooJoin);
	}
	@Override
	public int rejectDelReview(JooJoin jooJoin) {
		return session.update("adminRejectDelReview", jooJoin);
	}
	
	
//	게시판 / 리뷰 탭 나누기
	@Override
	public int qnaReTotal() {
		return session.selectOne("jhQnaReTotal");
	}
	@Override
	public List<JooJoin> qnaRelist(JooJoin qna_id) {
		List<JooJoin> qnaRelist =null;
		try {
			qnaRelist = session.selectList("jhQnaRelist", qna_id);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl qnaRelist Exception : "+e.getMessage());
		}
		return qnaRelist;
	}
	@Override
	public int reviewDelTotal() {
		return session.selectOne("jhReviewDelTotal");
	}
	@Override
	public List<JooJoin> reviewDellist(JooJoin review_id) {
		List<JooJoin> reviewDellist =null;
		try {
			reviewDellist = session.selectList("jhReviewDellist", review_id);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl reviewDellist Exception : "+e.getMessage());
		}
		return reviewDellist;
	}

	
//	일반 회원 검색
	@Override
	public int condUserCnt(JooJoin jooJoin) {
//		System.out.println("jjhDao AdminPageDaoImpl condUserCnt 시작");
		int conditionUserCount = 0;
		try {
			conditionUserCount = session.selectOne("condUserCnt", jooJoin);
//			System.out.println("jjhDao AdminPageDaoImpl conditionUserCount-> "+conditionUserCount);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl condUserCnt 에러: "+e.getMessage());
		}
		return conditionUserCount;
	}

	@Override
	public List<JooJoin> listSearchUser(JooJoin jooJoin) {
//		System.out.println("jjhDao AdminPageDaoImpl listSearchUser 시작");
		List<JooJoin> listSearchUser = null;
		try {
			listSearchUser = session.selectList("jhUserSearchList", jooJoin);
//			System.out.println("jjhDao AdminPageDaoImpl listSearchUser-> "+listSearchUser);
		} catch (Exception e) {
			System.out.println("jjhDao AdminPageDaoImpl listSearchUser 에러: "+e.getMessage());
		}
		return listSearchUser;
	}
	
	

	
	
}
