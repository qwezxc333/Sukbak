package com.example.sukbak.dao.lghDao;

import java.util.List;

import com.example.sukbak.model.GunJoin;
import com.example.sukbak.model.Out;
import com.example.sukbak.model.Review;
import com.example.sukbak.model.Review_Img;
import com.example.sukbak.model.Room;
import com.example.sukbak.model.Users;

public interface MypageDao {

			// 프로필 관련
			Users	 			getMyProfileInfo(String user_id);
			int 				existingNick(String newNickname);
			int 				updateMyProfile(Users users);
			int 				updatePassword(Users users);

			// 예약 관련
			List<GunJoin> 		getMyResvList(String user_id);
			Review 				getMyResvReviews(GunJoin gunJoin);
			int 				deleteSO(GunJoin gunJoin);
			int 				deletePay(GunJoin gunJoin);
			int 				deleteResv(GunJoin gunJoin);
			
			// 새 후기 관련;
			GunJoin 			getMyAccomInfo(Room room);
			int 				putMyReview(GunJoin gj);
			int 				getMaxImgNum(Review_Img revImg);			
			int 				putRevImgNum(Review_Img revImg);
			
			// 작성된 후기 관련
			List<Review> 		getMyReviewList(String user_id);
			List<Review_Img> 	getMyReviewImgList(List<Integer> myPayIds);
			List<Review_Img> 	getDelImgList(Review_Img delImgNums);
			int 				deleteMyReviewImg(int pay_id);
			int 				deleteMyReview(int pay_id);
			
			// 탈퇴 관련;
			int 				updateWithdraw(String user_id);
			int 				putWithdraw(Out outData);

}
