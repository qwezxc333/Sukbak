package com.example.S20230403.dao.lcgDao;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Users;



@Repository
@RequiredArgsConstructor
public class SellerReviewMgtDaoImpl implements SellerReviewMgtDao {
	private final SqlSession session;
	
	@Override
	public List<Accom> getMyAccoms(String sellerUser_id) {
		//System.out.println("dao getmyaccoms 시작");
		List<Accom> myAccoms = null;
		try {
			myAccoms = session.selectList("getMyAccoms", sellerUser_id);
			//System.out.println("dao getmyaccoms 사이즈 2나와야됨"+myAccoms.size());
		} catch (Exception e) {
			//System.out.println("dao getmyaccoms 에러 -> "+ e.getMessage());
			// TODO: handle exception
		}
		
		return myAccoms;
	}
	
	@Override
	public List<Room_Img> getMyAccomsImg() {
		//System.out.println("dao getMyaccomsimg 시작");
		List<Room_Img> myAccomsImgs = null;
		try {
			myAccomsImgs = session.selectList("cgGetRoom_img");
		} catch (Exception e) {
			//System.out.println("dao getMyaccomsimg 에러-> "+e.getMessage());
			// TODO: handle exception
		}
		return myAccomsImgs;
	}


	@Override
	public List<Accom> getMyInfo(String biz_id) {
		///System.out.println("dao getMyInfo 시작");
		List<Accom> myInfo = null;
		try {
			myInfo = session.selectList("getMyInfo", biz_id);
			//System.out.println("dao getMyInfo ->" +myInfo);
		} catch (Exception e) {
			//System.out.println("dao getMyInfo 에러-> "+e.getMessage());
			// TODO: handle exception
		}
		return myInfo;
	}

	@Override
	public List<Review> getMyReviews(Review review) {
		//System.out.println("다오 getMyReviews 시작");
		List<Review> myReviews = null;
		try {
			myReviews = session.selectList("getMyReviews", review);
			//System.out.println("다오 getMyReviews 사이즈 -> "+ myReviews.size());
		} catch (Exception e) {
			//System.out.println("다오 getMyReviews 에러 -> "+ e.getMessage());
			// TODO: handle exception
		}
		return myReviews;
	}

	@Override
	public Users getUserNicknames(String user_id) {
		//System.out.println("dao getuserNicname 시작");
		Users userNicknames = null;
		try {
			userNicknames = session.selectOne("getUserNicknames", user_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return userNicknames;
	}

	@Override
	public List<Review_Img> getReviewImgs(int pay_id) {
		//System.out.println("dao getReviewImgs 시작");
		List<Review_Img> review_imgs = null;
		try {
			review_imgs = session.selectList("getReviewImgs", pay_id);
		} catch (Exception e) {
			System.out.println("dao getReviewImgs 에러-> "+e.getMessage());
			// TODO: handle exception
		}
		return review_imgs;
	}


	@Override
	public int updateReviewDelRequestByPayId(Review review) {
		//System.out.println("dao updateReviewDelRequestByPayId 시작");
		int resultRequest = 0;
		try {
			resultRequest = session.update("updateReviewDelRequestByPayId", review);
			//System.out.println("dao updateReviewDelRequestByPayId result 1나와야됨-> "+resultRequest);
		} catch (Exception e) {
			//System.out.println("dao updateReviewDelRequestByPayId 에러 -> "+e.getMessage());
			// TODO: handle exception
		}
		return resultRequest;
	}

	@Override
	public Review getMyReviewConut(String biz_id) {
	//	System.out.println("dao getMyReviewConut 시작");
		Review totalReviewAndBiz_id = null;
		try {
			totalReviewAndBiz_id = session.selectOne("getMyReviewConut", biz_id);
		//	System.out.println("getMyReviewConut resultCount -> "+totalReviewAndBiz_id);
		} catch (Exception e) {
			System.out.println("dao getMyReviewConut 에러 -> "+e.getMessage());
			// TODO: handle exception
		}
		return totalReviewAndBiz_id;
	}
	
	// ajax
	@Override
	public List<Review> cgGetAjaxSortingReviewLists(Review review) {
		//System.out.println("dao cgGetAjaxSortingReviewLists 시작");
		List<Review> ajaxReviewSortingLists = null;
		try {
			ajaxReviewSortingLists = session.selectList("cgGetAjaxSortingReviewLists", review);
			//System.out.println("dao cgGetAjaxSortingReviewLists 사이즈 2개 나와야됨-> "+ajaxReviewSortingLists.size());
		} catch (Exception e) {
			//System.out.println("dao cgGetAjaxSortingReviewLists 에러 0-> "+e.getMessage());
			// TODO: handle exception
		}
		return ajaxReviewSortingLists;
	}






}
