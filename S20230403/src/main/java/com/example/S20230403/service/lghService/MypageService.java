package com.example.S20230403.service.lghService;

import java.util.List;

import com.example.S20230403.model.GunJoin;
import com.example.S20230403.model.Out;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

public interface MypageService {

			// 프로필 관련
			Users 				getMyProfileInfo(String user_id);
			int		 			updateMyProfile(Users users);
			int 				updatePassword(Users users);

			//예약 관련
			List<GunJoin> 		getMyResvList(String user_id);
//			List<GunJoin> 		GetMyResvImgList(GunJoin biz_id);
			Review 				getMyResvReviews(GunJoin gunJoin);
//			int 				cancelPay(int pay_id);
			int 				cancelResv(GunJoin gunJoin);
			
			// 새 후기 관련
			GunJoin 			getMyAccomInfo(Room room);
			int 				putMyReview(GunJoin gj);
			int 				getMaxImgNum(Review_Img revImg);
			int 				putRevImgNum(Review_Img revImg);
			
			// 작성된 후기 관련
//			List<Review> 		getMyReviewList(String user_id);
			List<Review> 		getMyReviewImages(String user_id);
			Review				getMyReviewUpdate(int pay_id);
			int 				updateMyReview(Review review);
			List<Review_Img> 	getDelImgList(Review_Img delImgNums);
			int 				deleteMyReview(int pay_id);
			
			// 탈퇴 관련
			int 				checkPassword(String user_id,String inputPW);
			int		 			updateWithdraw(String user_id);
			int 				putWithdraw(Out outData);
			
			// 1:1문의 관련
			Qna 				getMyQna(String user_id);


}
