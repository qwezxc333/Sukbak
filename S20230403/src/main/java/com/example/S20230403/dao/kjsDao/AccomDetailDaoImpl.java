package com.example.S20230403.dao.kjsDao;

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
public class AccomDetailDaoImpl implements AccomDetailDao {
	private final SqlSessionTemplate session;

	//숙소별 객실리스트 가져오기
	@Override
	public List<JinJoin> fetchAccomDetailRoomList(String biz_id, String checkIn, String checkOut) {
		
		Map<String, Object> reservedCheck = new HashMap<>();
		reservedCheck.put("biz_id", biz_id);
		reservedCheck.put("checkIn", checkIn);
		reservedCheck.put("checkOut", checkOut);
		
		List<JinJoin> accomDetailRoomList = session.selectList("jsAccomDetailRoomList", reservedCheck);
		
		return accomDetailRoomList;
	}
	
	//상세페이지 메인 숙소이름,주소 만 가져오기
	@Override
	public Accom fetchAccomBasicInfo(String biz_id) {
		Accom accomBasicInfo = session.selectOne("jsAccomBasicInfo", biz_id);
		
		return accomBasicInfo;
	}
	
	//상세페이지 메인 이미지 롤링 (모든객실 이미지 다가져오기)
	@Override
	public List<Room_Img> fetchRoomImgList(String biz_id) {
		List<Room_Img> roomImg = session.selectList("jsRoomImgList", biz_id);
		
		return roomImg;
	}
	
	//한 사업자에 대한 리뷰리스트 가져오기
	@Override
	public List<Review> fetchAccomReviewList(String biz_id) {
		List<Review> accomReview = session.selectList("jsAccomReviewList", biz_id);
		
		return accomReview;
	}

	//리뷰리스트에서 뽑은 List타입의 payIds를 이용해서 한 리뷰에 대한 pay_id와 그에 맞는 pay_id의 이미지'들' 가져오기
	@Override
	public List<Review_Img> fetchAccomReviewImgList(List<Integer> payIds) {
		List<Review_Img> accomReviewImg = session.selectList("jsAccomReviewImgList", payIds);
		
		return accomReviewImg;
	}

	//리뷰 총갯수, 평점
	@Override
	public Review fetchCalculateReview(String biz_id) {
		Review calculateReview = session.selectOne("jsCalculateReview", biz_id);
		
		return calculateReview;
	}

}
