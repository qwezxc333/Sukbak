package com.example.S20230403.service.jjhService;

import java.util.List;

import org.springframework.stereotype.Service;

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
		System.out.println("jjhService AdminPageServiceImpl userTotal 시작");
		System.out.println("jjhService AdminPageServiceImpl userTotal() : "+ad.userTotal());
		return ad.userTotal();
	}
	@Override
	public List<JooJoin> userlist(JooJoin jooJoin) {
		// TODO Auto-generated method stub
		return ad.userlist(jooJoin);
	}

//	사업자 회원 정보 전체 조회
	@Override
	public int accomTotal() {
		System.out.println("jjhService AdminPageServiceImpl accomTotal 시작");
		System.out.println("jjhService AdminPageServiceImpl accomTotal() : "+ad.accomTotal());
		return ad.accomTotal();
	}	
	@Override
	public List<JooJoin> accomlist(JooJoin jooJoin) {
		System.out.println("jjhService AdminPageServiceImpl accomlist start");
		return ad.accomlist(jooJoin);
	}

//	1대1 문의 / 불만접수 정보 전체 조회
	@Override
	public int qnaTotal() {
		System.out.println("jjhService AdminPageServiceImpl qnaTotal start");
		return ad.qnaTotal();
	}
	@Override
	public List<JooJoin> qnalist(JooJoin jooJoin) {
		System.out.println("jjhService AdminPageServiceImpl qnalist start");
		return ad.qnalist(jooJoin);
	}
	
//	리뷰 정보 전체 조회
	@Override
	public int reviewTotal() {
		System.out.println("jjhService AdminPageServiceImpl reviewTotal start");
		return ad.reviewTotal();
	}
	@Override
	public List<JooJoin> reviewlist(JooJoin jooJoin) {
		System.out.println("jjhService AdminPageServiceImpl reviewlist start");
		return ad.reviewlist(jooJoin);
	}
	

//	---------------------------------------------------------------
	
//	일반 회원 비활성화
	@Override
	public int delUsers(JooJoin jooJoin) {
		System.out.println("jjhService AdminPageServiceImpl delUsers start");
		return ad.delUsers(jooJoin);
	}

//	사업자 회원 비활성화
	@Override
	public int delAccom(JooJoin jooJoin) {
		System.out.println("jjhService AdminPageServiceImpl delAccom start");
		return ad.delAccom(jooJoin);
	}
	
//	QnA Reply 삭제
	@Override
	public int delQnARe(int qna_id) {
		System.out.println("jjhService AdminPageServiceImpl delQnARe start");
		return ad.delQnARe(qna_id);
	}

//	QnA 삭제
	@Override
	public int delQnA(int qna_id) {
		System.out.println("jjhService AdminPageServiceImpl delQnA start");
		return ad.delQnA(qna_id);
	}
	
//	Review 삭제
	@Override
	public int delReviewImg(int pay_id) {
		System.out.println("jjhService AdminPageServiceImpl delReviewImg start");
		int resultImg = ad.delReviewImg(pay_id);
		System.out.println("서비스 delReviewImg result 1나와야됨 -=> "+resultImg);
		if(resultImg != 0) {
			int resultRe = ad.delReview(pay_id);
			System.out.println("서비스 delReviewRe result 1나와야됨 -=> "+ resultRe);
		}else {
			System.out.println("리뷰 이미지가 없어요 DB를 확인해보세요. 혹은 다른 문제");
		}
		return resultImg;
	}
	
//	Review 삭제 요청 반려
	@Override
	public int rejectDelReview(JooJoin jooJoin) {
		System.out.println("jjhService AdminPageServiceImpl rejectDelReview start");
		return ad.rejectDelReview(jooJoin);
	}
	
//	---------------------------------------------------------------------
	
//	Review 답글 Form
	@Override
	public JooJoin detailQna(int qna_id) {
		System.out.println("jjhService AdminPageServiceImpl detailQna start");
		return ad.detailQna(qna_id);
	}
//	Review 답글 저장
	@Override
	public int saveReply(JooJoin jooJoin) {
		System.out.println("jjhService AdminPageServiceImpl insertReply start");
		return ad.saveReply(jooJoin);
	}
	



}
