package com.example.S20230403.dao.lysDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.JinJoin;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room_Img;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccomDetailDaoDaoImpl implements AccomDetailDao {
	private final SqlSessionTemplate session;

	@Override
	public List<Accom> fetchAccomList() {
		//system.out.println("다오 fetchAccomList 스타트");
		List<Accom> accomList = session.selectList("jsAccomList");
		
		return accomList;
	}
	
	//숙소별 객실리스트 가져오기
	@Override
	public List<JinJoin> fetchAccomDetailList(String biz_id, String checkIn, String checkOut) {
		//system.out.println("다오 fetchAccomDetailList 스타트");
		
		Map<String, Object> reservedCheck = new HashMap<>();
		reservedCheck.put("biz_id", biz_id);
		reservedCheck.put("checkIn", checkIn);
		reservedCheck.put("checkOut", checkOut);
		
		List<JinJoin> accomDetail = session.selectList("jsAccomDetailList", reservedCheck);
		return accomDetail;
	}
	
	//상세페이지 메인 숙소이름,주소 만 가져오기
	@Override
	public Accom fetchAccomBasicInfo(String biz_id) {
		//system.out.println("다오 fetchAccomBasicInfo 스타트");
		Accom accomBasicInfo = session.selectOne("jsAccomBasicInfo", biz_id);
		return accomBasicInfo;
	}
	
	//상세페이지 메인 이미지 롤링 (모든객실 이미지 다가져오기)
	@Override
	public List<Room_Img> fetchRoomImgList(String biz_id) {
		//system.out.println("다오 fetchRoomImgList 스타트");
		List<Room_Img> roomImg = session.selectList("jsRoomImgList", biz_id);
		return roomImg;
	}
	
	//한 사업자에 대한 리뷰리스트 가져오기
	@Override
	public List<Review> fetchAccomReviewList(String biz_id) {
		//system.out.println("다오 fetchAccomReviewList 스타트");
		List<Review> accomReview = session.selectList("jsAccomReviewList", biz_id);
		return accomReview;
	}

	//리뷰리스트에서 뽑은 List타입의 payIds를 이용해서 한 리뷰에 대한 pay_id와 그에 맞는 pay_id의 이미지'들' 가져오기
	@Override
	public List<Review_Img> fetchAccomReviewImgList(List<Integer> payIds) {
		//system.out.println("다오 fetchAccomReviewImgList 스타트");
		//system.out.println("payIds->"+payIds);
		List<Review_Img> accomReviewImg = session.selectList("jsAccomReviewImgList", payIds);
		//system.out.println("payIds->"+payIds);
		return accomReviewImg;
	}

	@Override
	public Review fetchCalculateReview(String biz_id) {
		Review calculateReview = session.selectOne("jsCalculateReview", biz_id);
		return calculateReview;
	}


}
