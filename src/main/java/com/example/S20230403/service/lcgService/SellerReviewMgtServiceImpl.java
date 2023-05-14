package com.example.S20230403.service.lcgService;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.dao.lcgDao.SellerReviewMgtDao;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerReviewMgtServiceImpl implements SellerReviewMgtService {
	private final SellerReviewMgtDao dao;
	// 내가 가지고 있는 업체를 가져오는 로직
	@Override
	public List<Accom> getMyAccoms(String sellerUser_id) {
		List<Accom> myAccoms = dao.getMyAccoms(sellerUser_id);
		List<Room_Img> myAccomsImg = dao.getMyAccomsImg();
		
		List<Accom> myAccomLists = new ArrayList<Accom>();
		
		// ACCOM 리스트랑   IMG랑  biz_id가 일치하면 이어주기.
		// 2중 for문임
		for (Accom accom : myAccoms) {
			
			for (Room_Img room_Img : myAccomsImg) {
				// 이미지의 biz_id와  accom의 biz_id가 같다면  accom객체가 가지고잇는 img에 넣어주기;
				if (accom.getBiz_id().equals(room_Img.getBiz_id())) {
					accom.setR_img(room_Img.getR_img());
					
				}
			}
			// 최종으로 넣은걸 리스트로 만들어서 최종 리스트가 되는거임
			myAccomLists.add(accom);
		}
		
		return myAccomLists;
	}

	// 업체 별로 가지고 있는 리뷰 가져오는 로직  
	@Override
	public List<Review> getMyReviews(Review p_review) {
		String biz_id = p_review.getBiz_id();
		//System.out.println("서비스 getmyReivews 시작");
		// 리뷰 받아오기
		List<Review> myReviews = dao.getMyReviews(p_review);
		
		//biz_id, biz_name, r_name, r_id를 가져오기 위한 로직
		List<Accom> myInfo = dao.getMyInfo(biz_id);
		
		
		// 리뷰를 하나씩 꺼내서 이미지를 저장시킬거임
		for(Review review : myReviews) {
			//닉네임을 불러오기 위한 user_id
			String user_id = review.getUser_id();
			
			// user_id로 닉네임 가져옴
			Users users = dao.getUserNicknames(user_id);
			//System.out.println("서비스 유저 아이디랑 일치해야됨 -> " +users.getNickname());
			if(users.getUser_id().equals(review.getUser_id())) {
				review.setNickname(users.getNickname());
			}
			
			//biz_id가 같다면 biz_name,r_name을 세팅해줄 거임. 
			for(Accom accom : myInfo) {
				if(review.getBiz_id().equals(accom.getBiz_id()) && review.getR_id().equals(accom.getR_id())) {
					review.setBiz_name(accom.getBiz_name());
					review.setR_name(accom.getR_name());
				}
			}
			
			// 리뷰이미지를 가져오기 위한 pay_id
			// 리뷰 이미지가 pay_id별로 한개당 3장이므로 리스트에 담아야됨.
			int pay_id = review.getPay_id();
			//System.out.println("서비스 유저아이디 닉네임 가져와야됨 -> "+user_id +" / "+pay_id);
			List<Review_Img> review_imgsByPay_id = dao.getReviewImgs(pay_id);
			//System.out.println("payid-> "+pay_id+" 리뷰이미지-> "+review_imgsByPay_id);
			
			// 리뷰 이미지만 뽑기 위한 for문과 리뷰 이미지만을 담을 리스트가 필요함.
			List<Review_Img> review_imgs = new ArrayList<Review_Img>();
			// for문으로 이미지만 뽑아서 review_imgs 여기에 넣습니다. 
			for(Review_Img review_Img : review_imgsByPay_id) {
				// payid가 같아야지 같은 리뷰니까 그걸로 유효성 검사를 하고 
				if(review.getPay_id() == review_Img.getPay_id()) {
					//System.out.println("두 개의 payid가 같은지 확인-> "+review.getPay_id()+" == "+review_Img.getPay_id()+"-> "+review_Img.getReview_img());
					review_imgs.add(review_Img);
				}
			
			}
			//System.out.println("잘 들어갔나 확인-> "+review_imgs);
			// 리뷰 객체에 필드로 private List<Review_Img> reviewImages; 만들어놓은거 그대로 사용할거임.
			// 그럼 여기에 [Review_Img(pay_id=26, review_img_id=0, review_img=tt.jpg),
			//			Review_Img(pay_id=26, review_img_id=0, review_img=ee.jpg), 
			//			Review_Img(pay_id=26, review_img_id=0, review_img=tt.jpg)] 이게 들어감
			review.setReviewImages(review_imgs);

			//System.out.println("이게 진짜 최종본-> "+myReviews);
			
		}
		
		
		
		return myReviews;
	}

	
	// 리뷰 delete 요청하는 로직
	@Override
	public int updateReviewDelRequestByPayId(Review review) {
		//System.out.println("service updateReviewDelRequestByPayId 시작");
		int resultRequest = dao.updateReviewDelRequestByPayId(review);
		return resultRequest;
	}
	// 리뷰 총 개수 가져오는 로직
	@Override
	public Review getMyReviewConut(String biz_id) {
		Review totalReviewAndBiz_id = dao.getMyReviewConut(biz_id);
		return totalReviewAndBiz_id;
	}
	
	// ajax biz_id와 kind를 가지고 필터링하는 로직
	@Override
	public List<Review> cgGetAjaxSortingReviewLists(Review reviewBiz_idKind) {
		//System.out.println("서비스 cgGetAjaxSortingReviewLists 시작");
		List<Review> ajaxReviewSortingLists = dao.cgGetAjaxSortingReviewLists(reviewBiz_idKind);
		
		String biz_id = reviewBiz_idKind.getBiz_id();
		//System.out.println("서비스 biz_id-> "+biz_id);
		List<Accom> myInfo = dao.getMyInfo(biz_id);
		
		// 리뷰를 하나씩 꺼내서 이미지를 저장시킬거임
		for(Review review : ajaxReviewSortingLists) {
			//닉네임을 불러오기 위한 user_id
			String user_id = review.getUser_id();
			
			// user_id로 닉네임 가져옴
			Users users = dao.getUserNicknames(user_id);
			//System.out.println("서비스 유저 아이디랑 일치해야됨 -> " +users.getNickname());
			if(users.getUser_id().equals(review.getUser_id())) {
				review.setNickname(users.getNickname());
			}
			
			//biz_id가 같다면 biz_name,r_name을 세팅해줄 거임. 
			for(Accom accom : myInfo) {
				if(review.getBiz_id().equals(accom.getBiz_id()) && review.getR_id().equals(accom.getR_id())) {
					review.setBiz_name(accom.getBiz_name());
					review.setR_name(accom.getR_name());
				}
			}
			
			// 리뷰이미지를 가져오기 위한 pay_id
			// 리뷰 이미지가 pay_id별로 한개당 3장이므로 리스트에 담아야됨.
			int pay_id = review.getPay_id();
			//System.out.println("서비스 유저아이디 닉네임 가져와야됨 -> "+user_id +" / "+pay_id);
			List<Review_Img> review_imgsByPay_id = dao.getReviewImgs(pay_id);
			//System.out.println("payid-> "+pay_id+" 리뷰이미지-> "+review_imgsByPay_id);
			
			// 리뷰 이미지만 뽑기 위한 for문과 리뷰 이미지만을 담을 리스트가 필요함.
			List<Review_Img> review_imgs = new ArrayList<Review_Img>();
			// for문으로 이미지만 뽑아서 review_imgs 여기에 넣습니다. 
			for(Review_Img review_Img : review_imgsByPay_id) {
				// payid가 같아야지 같은 리뷰니까 그걸로 유효성 검사를 하고 
				if(review.getPay_id() == review_Img.getPay_id()) {
					//System.out.println("두 개의 payid가 같은지 확인-> "+review.getPay_id()+" == "+review_Img.getPay_id()+"-> "+review_Img.getReview_img());
					review_imgs.add(review_Img);
				}
			
			}
			//System.out.println("잘 들어갔나 확인-> "+review_imgs);
			// 리뷰 객체에 필드로 private List<Review_Img> reviewImages; 만들어놓은거 그대로 사용할거임.
			// 그럼 여기에 [Review_Img(pay_id=26, review_img_id=0, review_img=tt.jpg),
			//			Review_Img(pay_id=26, review_img_id=0, review_img=ee.jpg), 
			//			Review_Img(pay_id=26, review_img_id=0, review_img=tt.jpg)] 이게 들어감
			review.setReviewImages(review_imgs);
	
			//System.out.println("이게 진짜 최종본-> "+ajaxReviewSortingLists);
			
		}
		
		
		
		return ajaxReviewSortingLists;
	}


	

}
