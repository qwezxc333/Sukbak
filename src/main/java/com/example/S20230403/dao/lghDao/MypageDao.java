package com.example.S20230403.dao.lghDao;

import java.util.List;

import com.example.S20230403.model.GunJoin;
import com.example.S20230403.model.Out;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

public interface MypageDao {

			// 프로필 관련
			Users	 			getMyProfileInfo(String user_id);
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
