package com.example.S20230403.service.jjhService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.dao.jjhDao.AdminPageDao;
import com.example.S20230403.model.JooJoin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPageServiceImpl implements AdminPageService {

	private final AdminPageDao ad;

//	일반 회원 정보 전체 조회
	@Override
	public int userTotal() {
//		System.out.println("jjhService AdminPageServiceImpl userTotal 시작");
//		System.out.println("jjhService AdminPageServiceImpl userTotal() : "+ad.userTotal());
		return ad.userTotal();
	}
	
	@Override
	public List<JooJoin> userlist(String user_id) {
		// TODO Auto-generated method stub
		return ad.userlist(user_id);
	}
	@Override
	public List<JooJoin> userlist(JooJoin jooJoin) {
		// TODO Auto-generated method stub
		return ad.userlist(jooJoin);
	}

//	사업자 회원 정보 전체 조회
	@Override
	public int accomTotal() {
//		System.out.println("jjhService AdminPageServiceImpl accomTotal 시작");
//		System.out.println("jjhService AdminPageServiceImpl accomTotal() : "+ad.accomTotal());
		return ad.accomTotal();
	}	
	@Override
	public List<JooJoin> accomlist(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl accomlist start");
		return ad.accomlist(jooJoin);
	}

//	1대1 문의 / 불만접수 정보 전체 조회
	@Override
	public int qnaTotal() {
//		System.out.println("jjhService AdminPageServiceImpl qnaTotal start");
		return ad.qnaTotal();
	}
	@Override
	public List<JooJoin> qnalist(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl qnalist start");
		return ad.qnalist(jooJoin);
	}
	
//	리뷰 정보 전체 조회
	@Override
	public int reviewTotal() {
//		System.out.println("jjhService AdminPageServiceImpl reviewTotal start");
		return ad.reviewTotal();
	}
	@Override
	public List<JooJoin> reviewlist(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl reviewlist start");
		return ad.reviewlist(jooJoin);
	}
	

//	---------------------------------------------------------------
	
//	일반 회원 비활성화
	@Override
	public int delUsers(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl delUsers start");
		return ad.delUsers(jooJoin);
	}

//	사업자 회원 비활성화
	@Override
	public int delAccom(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl delAccom start");
		return ad.delAccom(jooJoin);
	}
	
//	QnA Reply 삭제
	@Override
	public int delQnARe(int qna_id) {
//		System.out.println("jjhService AdminPageServiceImpl delQnARe start");
		return ad.delQnARe(qna_id);
	}

//	QnA 삭제
	@Override
	public int delQnA(int qna_id) {
//		System.out.println("jjhService AdminPageServiceImpl delQnA start");
		int resultdelQnARe = ad.delQnARe(qna_id);
		int resultdelQnA = 0;
//		System.out.println("서비스 resultdelQnARe-> "+resultdelQnARe);
		
		if(resultdelQnARe != 0) {
			resultdelQnARe = ad.delQnARe(qna_id);
			resultdelQnA = ad.delQnA(qna_id);
//			System.out.println("서비스 resultdelQnARe-> "+resultdelQnARe);
//			System.out.println("서비스 resultdelQnA-> "+resultdelQnA);
			
		}else if(resultdelQnARe == 0){
			resultdelQnA = ad.delQnA(qna_id);
//			System.out.println("서비스 resultdelQnA-> "+resultdelQnA);
		}else {
			System.out.println("서비스 delQnA 에러.");
		}
		
		return resultdelQnA;
	}
	
//	Review 삭제
	@Override
	@Transactional
	public int delReviewImg(int pay_id) {
//		System.out.println("jjhService AdminPageServiceImpl delReviewImg start");
		int resultImg = ad.delReviewImg(pay_id);
//		System.out.println("서비스 delReviewImg result -> "+resultImg);
		if(resultImg != 0) {
			int resultRe = ad.delReview(pay_id);
//			System.out.println("서비스 delReviewRe result -> "+ resultRe);
		}else {
			System.out.println("리뷰 이미지가 없어요 DB를 확인해보세요. 혹은 다른 문제");
		}
		return resultImg;
	}
	
//	Review 삭제 요청 반려
	@Override
	public int rejectDelReview(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl rejectDelReview start");
		return ad.rejectDelReview(jooJoin);
	}
	
//	---------------------------------------------------------------------
	
//	Review 답글 Form
	@Override
	public JooJoin detailQna(int qna_id) {
//		System.out.println("jjhService AdminPageServiceImpl detailQna start");
		return ad.detailQna(qna_id);
	}
//	Review 답글 저장
	@Override
	public int saveReply(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl insertReply start");
		return ad.saveReply(jooJoin);
	}
	
	
//	게시판 / 리뷰 탭 나누기
	@Override
	public int qnaReTotal() {
//		System.out.println("jjhService AdminPageServiceImpl qnaReTotal start");
		return ad.qnaReTotal();
	}
	@Override
	public List<JooJoin> qnaRelist(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl qnaRelist start");
		return ad.qnaRelist(jooJoin);
	}
	@Override
	public int reviewDelTotal() {
//		System.out.println("jjhService AdminPageServiceImpl reviewDelTotal start");
		return ad.reviewDelTotal();
	}
	@Override
	public List<JooJoin> reviewDellist(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl reviewDellist start");
		return ad.reviewDellist(jooJoin);
	}

//	일반 회원 검색
	@Override
	public int conditionUserTotal(JooJoin jooJoin) {
//		System.out.println("jjhService AdminPageServiceImpl conditionUserTotal 시작");
		int condUserCnt = ad.condUserCnt(jooJoin);
//		System.out.println("jjhService AdminPageServiceImpl conditionUserTotal condUserCnt-> "+condUserCnt);
		return condUserCnt;
	}

	@Override
	public List<JooJoin> listSearchUser(JooJoin jooJoin) {
		List<JooJoin> userSearchList = null;
//		System.out.println("jjhService AdminPageServiceImpl listSearchUser 시작");
		userSearchList = ad.listSearchUser(jooJoin);
//		System.out.println("jjhService AdminPageServiceImpl userSearchList.size()-> "+userSearchList.size());
		return userSearchList;
	}
	
	
	



}
