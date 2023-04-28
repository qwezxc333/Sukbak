package com.example.S20230403.service.kjsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.S20230403.dao.kjsDao.AccomDetailDao;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.JinJoin;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room_Img;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccomDetailServiceImpl implements AccomDetailService {
	private final AccomDetailDao ld;
	
	@Override
	public List<Accom> getAccomList() {
		System.out.println("서비스 accomList 스타트");
		List<Accom> accomLists = ld.fetchAccomList();
		
		return accomLists;
	}
	
	//숙소별 객실리스트 가져오기
	// 숙소별 객실리스트 및 객실*박수 계산한 총 금액 가져오기
	@Override
	public List<JinJoin> getAccomDetailRoomList(String biz_id, String checkIn, String checkOut) {
		System.out.println("서비스 accomDetail 스타트");
		List<JinJoin> accomDetailRoomList = ld.fetchAccomDetailRoomList(biz_id, checkIn, checkOut);
		
		//LocalDate로 파싱
		LocalDate localCheckIn = LocalDate.parse(checkIn);
		LocalDate localCheckOut = LocalDate.parse(checkOut);
		System.out.println("파싱 체크인->" + localCheckIn);
		System.out.println("파싱 체크아웃->" + localCheckOut);
	
		//일자를 구하려면 ChronoUnit.DAYS.between으로 구할 수 있는데, LocalDate 타입으로 파싱해야됌
		long days = ChronoUnit.DAYS.between(localCheckIn, localCheckOut);
		
		for(JinJoin roomPrice : accomDetailRoomList) {
			int originRoomPrice = roomPrice.getR_price();
			int totalRoomPrice =(int) (originRoomPrice * days);
			System.out.println("originRoomPrice->" + originRoomPrice);
			System.out.println("totalRoomPrice->" + totalRoomPrice);
			//조회용필드 totalRoomPrice에 setter로 구한거 담아줌
			roomPrice.setTotalRoomPrice(totalRoomPrice);
		}
		
		return accomDetailRoomList;
	}
	
	//상세페이지 메인 이미지 롤링 (모든객실 이미지 다가져오기)
	@Override
	public List<Room_Img> getRoomImgList(String biz_id) {
		System.out.println("서비스 roomImgList 스타트");
		List<Room_Img> roomImg = ld.fetchRoomImgList(biz_id);
		return roomImg;
	}
		
	//상세페이지 메인 숙소이름,주소, 기본정보
	@Override
	public Accom getAccomBasicInfo(String biz_id) {
		System.out.println("서비스 accomBasicInfo");
		
		Accom accomBasicInfo = ld.fetchAccomBasicInfo(biz_id);
		
		accomBasicInfo.setPool(convertYNtoText(accomBasicInfo.getPool(), "수영장"));
		accomBasicInfo.setParking(convertYNtoText(accomBasicInfo.getParking(), "주차장")); 
		accomBasicInfo.setCafe(convertYNtoText(accomBasicInfo.getCafe(), "카페")); 
		accomBasicInfo.setRestaurant(convertYNtoText(accomBasicInfo.getRestaurant(), "레스토랑")); 
		accomBasicInfo.setStore(convertYNtoText(accomBasicInfo.getStore(), "편의점")); 
		accomBasicInfo.setSauna(convertYNtoText(accomBasicInfo.getSauna(), "사우나")); 
		accomBasicInfo.setLaundry(convertYNtoText(accomBasicInfo.getLaundry(), "세탁시설")); 
		accomBasicInfo.setFitness(convertYNtoText(accomBasicInfo.getFitness(), "헬스장")); 
		
		return accomBasicInfo;
	}
	
	//편의시설 YN 유무로 텍스트 바꾸는 비지니스 로직
	private String convertYNtoText(String value, String setText) {
		if(value.equals("Y")) {
			return setText;
		} else {
			return "";
		}
	}
//	private String convertYNtoText1(String value, String setText) {
//		return value.equals("Y") ? setText : "";
//	}
	
	
	
	//객실리스트 가져와 pay_id를 따로 저장하는 로직
	//(담은걸 리류사진 부르는곳에 매개변수로 넘겨줘서 리뷰사진 xml에 동적으로 pay_id를 줄거임)
	@Override
	public List<Review> getAccomReviewWithImagesList(String biz_id) {
		// 한 사업자에 대해 리뷰 리스트를 가져온다
		List<Review> accomReview = ld.fetchAccomReviewList(biz_id);
		// payIds를 저장할 ArrayList 생성
		List<Integer> payIds = new ArrayList<>();
		
		// accomReview 리뷰리스트에서 한 사업자에 대한 pay_id를 payIds에 저장
		for(Review review : accomReview) {
			payIds.add(review.getPay_id());
			System.out.println("(review.getPay_id()->" + review.getPay_id());
		}
		System.out.println("payIds->" + payIds);
		// payIds가 IN에 포함된 리뷰 이미지 리스트를 가져온다
		//reviewImages는 그 사진 목록 저장하는 필드(Review객체에 생성했음)
		List<Review_Img> reviewImages = ld.fetchAccomReviewImgList(payIds);		
		System.out.println("reviewImages->"+reviewImages);
		
		// accomReview 리뷰리스트에서 
		for (Review review : accomReview) {
			// 현재 Review 객체와 같은 pay_id를 가진 Review_Img 객체들을 저장할 새로운 리스트
	        List<Review_Img> reviewImagesForCurrentReview = new ArrayList<>();
	        
	        
	        // 모든 review_Img객체에 대해서 반복
	        for (Review_Img reviewImage : reviewImages) {
	        	// Review_Img의 pay_id와 Review의 pay_id가 일치하는것만 reviewImagesForCurrentReview 리스트에 저장
	            if (reviewImage.getPay_id() == review.getPay_id()) {
	                reviewImagesForCurrentReview.add(reviewImage);
	            }
	        }
	        System.out.println("reviewImagesForCurrentReview->"+reviewImagesForCurrentReview);
	        //Review객체에 생성한 reviewImages(pay_id가 일치하는 이미지만 저장하는 필드)에 setter로 저장
	        review.setReviewImages(reviewImagesForCurrentReview);
	    }
		return accomReview;
	}
	
	//리뷰 총갯수, 평점
	@Override
	public Review getCalculateReview(String biz_id) {
		Review calculateReview = ld.fetchCalculateReview(biz_id);

		//Math는 Long으로 받아서 10.0으로 다시 나눠야함 
		double calAvgReview = (Math.round((calculateReview.getAvgReview() * 10)) / 10.0 ); 
		calculateReview.setAvgReview(calAvgReview);
		
		return calculateReview;
	}
	

}
