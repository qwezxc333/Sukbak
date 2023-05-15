package com.example.S20230403.dao.lghDao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.GunJoin;
import com.example.S20230403.model.Out;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MypageDaoImpl implements MypageDao {

	private final SqlSessionTemplate session;

	@Override
	public Users getMyProfileInfo(String user_id) {
		System.out.println("MypageDAO getMyProfileInfo Start...");
		Users myProfileInfo = new Users();
		try {
			myProfileInfo = session.selectOne("getMyProfileInfo", user_id);
			System.out.println("MypageDAO myProfileInfo getNickname-> " + myProfileInfo.getNickname());
		} catch (Exception e) {
			System.out.println("MypageDAO myProfileInfo Exception-> " + e.getMessage());
		}
		return myProfileInfo;
	}

	@Override
	public int updateMyProfile(Users users) {
		//System.out.println("MypageDAO updateProfile start...");
		int updateMyProfile = 0;
		try {
			updateMyProfile = session.update("updateMyProfile", users);
		} catch (Exception e) {
			//System.out.println("MypageDAO updateMyProfile Exception-> " + e.getMessage());
		}
		return updateMyProfile;
	}
	
	@Override
	public int updatePassword(Users users) {
		//System.out.println("MypageDAO updatePassword start...");
		int updatePwd = 0;
		try {
			updatePwd = session.update("updatePassword", users);
			//System.out.println("MypageDAO updatePwd-> " + updatePwd);
		} catch (Exception e) {
			//System.out.println("MypageDAO updatePassword Exception-> " + e.getMessage());
		}
		return updatePwd;
	}
	
	
	
	@Override
	public List<GunJoin> getMyResvList(String user_id) {
		List<GunJoin> myResvList = null;
		//System.out.println("MypageDAO getMyResvList Start...");
		try {
			myResvList = session.selectList("getMyResvList", user_id);
			//System.out.println("MypageDAO getMyResvList myResvList.size()-> " + myResvList.size());
		} catch (Exception e) {
			//System.out.println("MypageDAO getMyResvList e.getMessage()-> " + e.getMessage());
		}
		return myResvList;
	}

	@Override
	public Review getMyResvReviews(GunJoin gunJoin) {
		//System.out.println("MypageDAO getMyResvReviews start");
		Review myResvReviews = null;
		try {
			myResvReviews = session.selectOne("getMyResvReviews", gunJoin);
			//System.out.println("MypageDAO myResvReviews-> " + myResvReviews);
		} catch (Exception e) {
			//System.out.println("MypageDAO getMyResvReviews Exception-> " + e.getMessage());
		}
		return myResvReviews;
	}

	@Override
	public int deleteSO(GunJoin gunJoin) {
		//System.out.println("MypageDAO deleteSO start");
		int deleteSO = 0;
		try {
			deleteSO = session.delete("deleteSO", gunJoin);
			//System.out.println("MypageDAO deleteSO-> " + deleteSO);
		} catch (Exception e) {
			//System.out.println("MypageDAO deleteSO Exception-> " + e.getMessage());
		}
		return deleteSO;
	}

	@Override
	public int deletePay(GunJoin gunJoin) {
		//System.out.println("MypageDAO deletePay start");
		int deletePay = 0;
		try {
			deletePay = session.delete("deletePay", gunJoin);
			//System.out.println("MypageDAO deletePay-> " + deletePay);
		} catch (Exception e) {
			//System.out.println("MypageDAO deletePay Exception-> " + e.getMessage());
		}
		return deletePay;
	}

	@Override
	public int deleteResv(GunJoin gunJoin) {
		//System.out.println("MypageDAO deleteResv start");
		int deleteResv = 0;
		try {
			deleteResv = session.delete("deleteResv", gunJoin);
			//System.out.println("MypageDAO deleteResv-> " + deleteResv);
		} catch (Exception e) {
			//System.out.println("MypageDAO deleteResv Exception-> " + e.getMessage());
		}
		return deleteResv;
	}


	
	@Override
	public GunJoin getMyAccomInfo(Room room) {
		//System.out.println("MypageDAO getMyAccomInfo start...");
		GunJoin myAccomInfo = new GunJoin();
		try {
			myAccomInfo = session.selectOne("getMyAccomInfo", room);
			//System.out.println("MypageDAO getMyAccomInfo.getBizname-> " + myAccomInfo.getBiz_name());
		} catch (Exception e) {
			//System.out.println("MypageDAO getMyAccomInfo Exception-> " + e.getMessage());
		}
		return myAccomInfo;
	}
	
	@Override
	public int putMyReview(GunJoin gj) {
		//System.out.println("MypageDAO putMyReview Start...");
		int putMyReview = 0;
		try {
			putMyReview = session.insert("putMyReview", gj);
		} catch (Exception e) {
			//System.out.println("MypageDAO putMyReview Exception-> " + e.getMessage());
		}
		return putMyReview;
	}

	@Override
	public int getMaxImgNum(Review_Img revImg) {
		//System.out.println("MypageDAO getMaxImgNum start");
		int imgNum = 0;
		try {
			imgNum = session.selectOne("getMaxImgNum", revImg);
			//System.out.println("MypageDAO maxImgNum-> " + imgNum);
		} catch (Exception e) {
			//System.out.println("MypageDAO getMaxImgNum Exception-> " + e.getMessage());
		}
		return imgNum;
	}

	@Override
	public int putRevImgNum(Review_Img revImg) {
		//System.out.println("MypageDAO putRevImgNum start");
		int putRevImgNumResult = 0;
		try {
			putRevImgNumResult = session.insert("putRevImgNum", revImg);
			//System.out.println("MypageDAO putRevImgNum result-> " + putRevImgNumResult);
		} catch (Exception e) {
			//System.out.println("MypageDAO putRevImgNum Exception-> " + e.getMessage());
		}
		return putRevImgNumResult;
	}
	
	
	
	@Override
	public List<Review> getMyReviewList(String user_id) {
		//System.out.println("MypageDAO getMyReviewList start...");
		List<Review> myReviewLists = null;
		try {
			myReviewLists = session.selectList("getMyReviewList", user_id);
			//System.out.println("MypageDAO myReviewList.size()-> " + myReviewLists.size());
		} catch (Exception e) {
			//System.out.println("MypageDAO getMyReviewList Exception-> " + e.getMessage());
		}
		return myReviewLists;
	}
	
	@Override
	public List<Review_Img> getMyReviewImgList(List<Integer> myPayIds) {
		//System.out.println("MypageDAO getMyReviewImgList start...");
		List<Review_Img> myReviewImgList = null;
		try {
			myReviewImgList = session.selectList("getMyReviewImgList", myPayIds);
			//System.out.println("MypageDAO myReviewImgList.size()-> " + myReviewImgList.size());
		} catch (Exception e) {
			//System.out.println("MypageDAO getMyReviewImgList Exception-> " + e.getMessage());
		}
		return myReviewImgList;
	}
	
	@Override
	public List<Review_Img> getDelImgList(Review_Img delImgNums) {
		//System.out.println("MypageDAO getDelImgList start");
		List<Review_Img> delImgList = null;
		try {
			delImgList = session.selectList("getDelImgList", delImgNums);
			//System.out.println("MypageDAO delImgList-> " + delImgList);
		} catch (Exception e) {
			//System.out.println("MypageDAO getDelImgList Exception-> " + e.getMessage());
		}
		return delImgList;
	}
	
	@Override
	public int deleteMyReviewImg(int pay_id) {
		//System.out.println("MypageDAO deleteReviewImg start");
		int deleteMyReviewImg = 0;
		try {
			deleteMyReviewImg = session.delete("deleteMyReviewImg", pay_id);
			//System.out.println("MypageDAO deleteMyReview result-> " + deleteMyReviewImg);
		} catch (Exception e) {
			//System.out.println("MypageDAO deleteMyReviewImg Exception-> " + e.getMessage());
		}
		return deleteMyReviewImg;
	}
	
	@Override
	public int deleteMyReview(int pay_id) {
		//System.out.println("MypageDAO deleteMyReview start...");
		int deleteMyReview = 0;
		try {
			
			deleteMyReview = session.delete("deleteMyReview", pay_id);
			//System.out.println("MypageDAO deleteMyReview result-> " + deleteMyReview);
		} catch (Exception e) {
			//System.out.println("MypageDAO deleteMyReview Exception-> " + e.getMessage());
		}
		return deleteMyReview;
	}

	
	
	@Override
	public int updateWithdraw(String user_id) {
		//System.out.println("MypageDAO updateWithdraw Start...");
		int updateWithdraw = 0;
		//System.out.println("MypageDAO user_id-> " + user_id);
		try {
			updateWithdraw = session.update("updateWithdraw", user_id);
			//System.out.println("MypageDAO updateWithdraw result-> " + updateWithdraw);
		} catch (Exception e) {
			//System.out.println("MypageDAO updateWithdraw Exception-> " + e.getMessage());
		}
		return updateWithdraw;
	}

	@Override
	public int putWithdraw(Out outData) {
		//System.out.println("MypageDAO putWithdraw start...");
		int putWithdraw = 0;
		try {
			putWithdraw = session.insert("putWithdraw", outData);
		} catch (Exception e) {
			//System.out.println("MypageDAO putWithdraw Exception-> " + e.getMessage());
		}
		return putWithdraw;
	}

}
