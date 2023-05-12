package com.example.S20230403.service.lghService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.dao.lghDao.MypageDao;
import com.example.S20230403.model.GunJoin;
import com.example.S20230403.model.Out;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

import groovyjarjarantlr4.v4.parse.ANTLRParser.parserRule_return;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
	
	private final MypageDao mypageDao;

	@Override
	public Users getMyProfileInfo(String user_id) {
		System.out.println("MypageServiceImpl getMyProfileInfo...");
		Users myProfileInfo = mypageDao.getMyProfileInfo(user_id);
		return myProfileInfo;
	}

	@Override
	public int updateMyProfile(Users users) {
		//System.out.println("MypageServiceImpl updateProfile...");
		int updateMyProfile = mypageDao.updateMyProfile(users);
		return updateMyProfile;
	}

	@Override
	public int updatePassword(Users users) {
		//System.out.println("MypageService updatePassword...");
		int updatePwd = mypageDao.updatePassword(users);
		//System.out.println("MypageService updatePassword result-> " + updatePwd);
		return updatePwd;
	}
	
	
	
	@Override
	public List<GunJoin> getMyResvList(String user_id) {
		List<GunJoin> myResvList = null;
		//System.out.println("MypageServiceImpl getMyResvList...");
		myResvList = mypageDao.getMyResvList(user_id);
		//System.out.println("MypageServiceImpl getMyResvList.size()-> " + myResvList.size());
		return myResvList;
	}

	@Override
	public Review getMyResvReviews(GunJoin gunJoin) {
		//System.out.println("MypageService getMyResvReviews");
		Review myResvReviews = mypageDao.getMyResvReviews(gunJoin);
		
		return myResvReviews;
	}

	@Override
	@Transactional
	public int cancelResv(GunJoin gunJoin) {
		//System.out.println("MypageService cancelResv");
		int cancelSO = mypageDao.deleteSO(gunJoin);
		int cancelPay = mypageDao.deletePay(gunJoin);
		int cancelResv = mypageDao.deleteResv(gunJoin);
		
		return cancelSO + cancelPay + cancelResv;
	}
	
	

	@Override
	public GunJoin getMyAccomInfo(Room room) {
		//System.out.println("MypageServiceImpl getMyAccomInfo...");
		GunJoin myAccomInfo = mypageDao.getMyAccomInfo(room);
		return myAccomInfo;
	}

	@Override
	public int putMyReview(GunJoin gj) {
		int putMyReview = 0;
		//System.out.println("MypageServiceImpl putMyReview...");
		putMyReview = mypageDao.putMyReview(gj);
		return putMyReview;
	}
	
	@Override
	public int getMaxImgNum(Review_Img revImg) {
		//System.out.println("MypageServiceImpl getMaxImgNum...");
		int imgNum = mypageDao.getMaxImgNum(revImg);
		return imgNum;
	}
	
	@Override
	public int putRevImgNum(Review_Img revImg) {
		//System.out.println("MypageService putRevImgNum...");
		int putRevImgNumResult = mypageDao.putRevImgNum(revImg);
		return putRevImgNumResult;
	}
	
	
	
	@Override
	public List<Review> getMyReviewImages(String user_id) {
		//System.out.println("MypageServiceImpl getMyReviewImages...");
		
		// 로그인한 사용자의 리뷰 list 불러오기
		List<Review> myReviewList = mypageDao.getMyReviewList(user_id);
		List<Review> myReviewImgList = new ArrayList<Review>(myReviewList);
		
		// 위의 list에서 pay_id만 따로 저장할 list 선언
		List<Integer> myPayIds = new ArrayList<>();
		
		// pay_id 리스트에 myReviewList에 포함된 pay_id값만 저장하기
		for (Review myReview : myReviewImgList) {
			myPayIds.add(myReview.getPay_id());
			//System.out.println("review.getPay_id()-> " + myReview.getPay_id());
		}
		//System.out.println("myPayIds-> " + myPayIds);
		
		// payIds값에 해당하는 review_img값 list로 불러오기
		List<Review_Img> myReviewImages = mypageDao.getMyReviewImgList(myPayIds);
		//System.out.println("myReviewImages-> " + myReviewImages);
		
		// Review테이블의 pay_id와 Review_img테이블의 pay_id가 일치하는 값을 list로 저장하기
		List<Review_Img> matchingReviewImages = null;
		
		for (Review myReview : myReviewImgList) {
			matchingReviewImages = new ArrayList<>();
			for (Review_Img myReviewImg : myReviewImages) {
				if (myReviewImg.getPay_id() == myReview.getPay_id()) {
					matchingReviewImages.add(myReviewImg);
				}
			}
			// Review 타입으로 리턴할 수 있도록 Review 객체에 matchingReviewImages값을 세팅하기
			myReview.setMatchingReviewImages(matchingReviewImages);
		}
		//System.out.println("matchingReviewImages-> " + matchingReviewImages);
		
		return myReviewImgList;
	}

	@Override
	public List<Review_Img> getDelImgList(Review_Img delImgNums) {
		//System.out.println("MypageService getDelImgList");
		List<Review_Img> delImgList = mypageDao.getDelImgList(delImgNums);
		
		return delImgList;
	}
	
	@Override
	@Transactional
	public int deleteMyReview(int pay_id) {
		//System.out.println("MypageServiceImpl deleteMyReview...");
		int deleteMyReviewImg = mypageDao.deleteMyReviewImg(pay_id);
		int deleteMyReview = mypageDao.deleteMyReview(pay_id);
		return deleteMyReviewImg + deleteMyReview;
	}

	

	@Override
	public int updateWithdraw(String user_id) {
		int updateWithdraw = 0;
		//System.out.println("MypageServiceImpl updateWithdraw...");
		updateWithdraw = mypageDao.updateWithdraw(user_id);
		return updateWithdraw;
	}

	@Override
	public int putWithdraw(Out outData) {
		//System.out.println("MypageServiceImpl putWithdraw...");
		int putWithdraw = mypageDao.putWithdraw(outData);
		return putWithdraw;
	}

}
