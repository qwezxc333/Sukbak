package com.example.S20230403.controller.lghController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.GunJoin;
import com.example.S20230403.model.Out;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Review;
import com.example.S20230403.model.Review_Img;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;
import com.example.S20230403.service.lghService.MypageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MypageController {
	
	private final MypageService mypageService;
	private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	// 마이페이지 메인 화면
	@RequestMapping("/commonUser/mypage")
	public String getMypage() {
		
		return "/views/mypage/mypage";
	}
	
	
	
//	=============== 프로필 관련 ===============
	// 내 프로필 화면 조회(View)
	@RequestMapping("/commonUser/myProfile")
	public String getMyProfile(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("MypageController myProfileView Start...");
		
		String user_id = userDetail.getUsername();
		Users myProfileInfo = mypageService.getMyProfileInfo(user_id);
		
		model.addAttribute("myProfileInfo", myProfileInfo);
		
		return "/views/mypage/myProfile";
	}
	
	// 내 프로필 수정(프로세스)
	@PostMapping("/commonUser/updateMyProfile")
	public String updateMyProfile(@AuthenticationPrincipal PrincipalDetail userDetail, Users users) {
		System.out.println("MypageController updateMyProfile Start...");
		System.out.println("MypageController updateMyProfile 닉네임->..." + users.getNickname());
		
		users.setUser_id(userDetail.getUsername());
		int updateMyProfile = mypageService.updateMyProfile(users);
		System.out.println("MypageController updateMyProfile result-> " + updateMyProfile);
		
		return "redirect:/commonUser/myProfile";
	}
	
	// 비밀번호 수정(View)
	@RequestMapping("/commonUser/myPwChange")
	public String getMyPwCheck(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("MypageController myPwCheck Start...");
		
		String user_id = userDetail.getUsername();
		Users myPassword = mypageService.getMyProfileInfo(user_id);
		
		model.addAttribute("myPassword", myPassword);
		
		return "/views/mypage/myPwChange";
	}
	
	// 비밀번호 수정(프로세스)
	@RequestMapping("/commonUser/updateMyPassword")
	public String updatePassword(@AuthenticationPrincipal PrincipalDetail userDetail, Users users,
								 @RequestParam("newPwd") String newPwd) {
		System.out.println("MypageController updatePassword start...");
		System.out.println("newPwdBefore-> " + newPwd);
		
		String encodedPwd = new BCryptPasswordEncoder().encode(newPwd);
		System.out.println("newPwdAfter-> " + encodedPwd);
		users.setPassword(encodedPwd);
		users.setUser_id(userDetail.getUsername());
		
		int updatePwd = mypageService.updatePassword(users);
		
		return "redirect:/commonUser/mypage";
	}
	
	
	
//  =============== 예약 관련 ===============
	// 예약 내역 조회(View)
	@RequestMapping("/commonUser/myReserved")
	public String getMyResvLists(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("MypageController getMyResvLists Start...");
		
		// 예약 정보 불러오기
		String user_id = userDetail.getUsername();
		System.out.println("MypageController getMyResvLists user_id-> " + user_id);
		List<GunJoin> myResvList = mypageService.getMyResvList(user_id);
		System.out.println("MypageController list getMyResvList.size()-> " + myResvList.size());
		
		// 예약 내역의 이미지 불러오기
//		List<GunJoin> myResvImgList = mypageService.GetMyResvImgList(biz_id);
//		System.out.println("MypageController list myResvImgList.size()-> " + myResvImgList.size());
		
		model.addAttribute("myResvList", myResvList);
//		model.addAttribute("myResvImgList", myResvImgList);
		
		return "/views/mypage/myReserved";
	}
	
	
	
//  =============== 리뷰 관련 ===============
	// 새 후기 작성 페이지(View)
	@RequestMapping("/commonUser/myNewReview")
	public String getMyNewReview(@AuthenticationPrincipal PrincipalDetail userDetail, 
								 @RequestParam("biz_id") String biz_id, @RequestParam("r_id") int r_id,
								 Room room, Review review, Review_Img revImg, Model model) {
		System.out.println("MypageController getMyNewReview Start...");
		
		// 사용자 닉네임 가져오기
		String user_id = userDetail.getUsername();
		System.out.println("MypageController getMyNewReview user_id-> " + user_id);
		Users myNickname = mypageService.getMyProfileInfo(user_id);
		
		// 숙박업체 정보 가져오기
		System.out.println("MypageController getMyNewReview biz_id-> " + biz_id);
		System.out.println("MypageController getMyNewReview r_id-> " + r_id);
		GunJoin myAccomInfo = mypageService.getMyAccomInfo(room);
		
		model.addAttribute("myNickname", myNickname);
		model.addAttribute("myAccomInfo", myAccomInfo);
		model.addAttribute("revImg", revImg);
		model.addAttribute("pay_id", review.getPay_id());
		
		return "/views/mypage/myNewReview";
	}
	
	// 새 후기 작성하기(프로세스)
	@RequestMapping("/commonUser/putReview")
	public String putMyReview(GunJoin gj, Model model, MultipartFile[] files,
							  Review review, Review_Img revImg) throws Exception {
		System.out.println("1. MypageController putMyReview Start...");
		
		// Review 테이블에 Insert
		int rating = gj.getRating();
		System.out.println("별점 점수-> " + rating);
		int putMyReview = mypageService.putMyReview(gj);
		System.out.println("2. MypageController putMyReview result-> " + putMyReview);
		
		// 이미지 업로드(Review_Img 테이블에 Insert)
		System.out.println("3. MypageController revImgUpload start...");
		String uploadPath = "src/main/resources/static/img/review/";
		String savedName = null;
		
		for (MultipartFile file : files) {
			System.out.println("4. revImg for문 실행...");
			// 파일 확장자
			final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			// 서버에 저장할 파일명
			if (file != null && !file.isEmpty()) {
				savedName = uploadFile(file.getBytes(), uploadPath, revImg, extension);
				System.out.println("6. revImgController savedName-> " + savedName);
			} else {
				System.out.println("선택된 파일이 없습니다.");
			}
		}
		
		if (putMyReview > 0 && savedName != null) {
			return "redirect:myReviews";
		} else {
			model.addAttribute("msg", "입력 실패");
			return "forward:/commonUser/myNewReview";
		}
	}	
		
	// savedName(파일명) 정의
	private String uploadFile(byte[] fileData, String uploadPath, 
							  Review_Img revImg, String extension) throws IOException {
		System.out.println("5. MypageController uploadFile start...");
		// Review_img_ID(MAX) 키 정의
		// 현재 작성중인 Review의 pay_id와 일치하는 review값 중 MAX Review_img_id 값을 가져와 거기에 1을 더한다.
		int imgNum = mypageService.getMaxImgNum(revImg);
		imgNum += 1;
		System.out.println("MypageController uploadFile imgNum-> " + imgNum);
		
		// 파일명	형식: (pay_id)-(review_img_id).(확장자명)--> 예) 26-1.jpg
		String savedName = revImg.getPay_id() + "-" + imgNum + "." + extension;
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);
		System.out.println("MypageController uploadFile savedName->" + savedName);
		
		// 위에서 정의한 Review_img_ID와 파일명으로 데이터 setting
		revImg.setReview_img_id(imgNum);
		revImg.setReview_img(savedName);
		System.out.println("MypageController uploadFile revImg-> " + revImg);
		
		// Review_img 테이블에 setting한 정보 Insert
		int putRevImgNum = mypageService.putRevImgNum(revImg);
		System.out.println("revImgControlelr putRevImg result-> " + putRevImgNum);
		
		return savedName;
	}
	
	
	
	// 작성한 후기 조회(View)
	@RequestMapping("/commonUser/myReviews")
	public String getMyReviews(@AuthenticationPrincipal PrincipalDetail userDetail, 
							   Model model) {
		System.out.println("MypageController getMyReviews Start...");
		String user_id = userDetail.getUsername();
		
		// 리뷰 테이블의 데이터 불러오기
//		List<Review> myReviewList = mypageService.getMyReviewList(user_id);
//		System.out.println("MypageController myReviewList-> " + myReviewList);
		
		// 리뷰 이미지 테이블의 데이터 불러오기
		List<Review> myReviewImgList = mypageService.getMyReviewImages(user_id);
		System.out.println("MypageController myReviewImgList-> " + myReviewImgList);
		
//		model.addAttribute("myReviewList", myReviewList);
		model.addAttribute("myReviewImgList", myReviewImgList);
		
		return "/views/mypage/myReviews";
	}
	
	// 작성한 후기 수정(View)
	@RequestMapping("/commonUser/getMyReviewUpdate")
	public String getMyReviewUpdate(@AuthenticationPrincipal PrincipalDetail userDetail, 
									Review review, Model model) {
		System.out.println("MypageController getMyReviewUpdate Start...");
		int pay_id = review.getPay_id();
		System.out.println("getMyReviewUpdate pay_id-> " + pay_id);
		
		Review getMyRevUpdate = mypageService.getMyReviewUpdate(pay_id);
		System.out.println("MypageController getMyReviewUpdate-> " + getMyRevUpdate);
		
		model.addAttribute("getMyRevUpdate", getMyRevUpdate);
		
		return "/views/mypage/myReviewUpdatePage";
	}

	// 작성한 후기 수정(프로세스)--> 이미지 제외
	@RequestMapping("/commonUser/updateMyReview")
	public String updateMyReview(@AuthenticationPrincipal PrincipalDetail userDetail, 
								 @RequestParam("pay_id") int pay_id, Review review) {
		System.out.println("MypageController updateMyReview Start...");
		review.setPay_id(pay_id);
		System.out.println("MypageController updateMyReview pay_id-> " + review);
		int updateMyReview = mypageService.updateMyReview(review);
		
		System.out.println("MypageController updateMyReview-> " + updateMyReview);
		
		return "redirect:/commonUser/myReviews";
	}
	
	// 이미지는 업데이트하는 것보다 삭제 후 재업로드가 용이하므로 update가 아닌 delete & insert의 과정을 거쳐야 한다.
	// 이미지 삭제 프로세스
	
	
	
	@RequestMapping("/commonUser/deleteMyReview")
	public String deleteMyReview(@AuthenticationPrincipal PrincipalDetail userDetail, Review review) {
		System.out.println("MypageController deleteMyReview Start...");
		int pay_id = review.getPay_id();
		System.out.println("MypageController deleteMyReview pay_id-> " + pay_id);
		
		int deleteMyReview = mypageService.deleteMyReview(pay_id);
		System.out.println("MypageController deleteMyReview delRev-> " + deleteMyReview);
		
		return "redirect:/commonUser/myReviews";
	}

	
	
//  =============== 탈퇴 관련 ===============
	// 회원 탈퇴 화면(View)
	@RequestMapping("/commonUser/myWithdraw")
	public String getWithdrawInfo(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("MypageController getWithdrawInfo Start...");
		String user_id = userDetail.getUsername();
		 
		Users withdrawInfo = mypageService.getMyProfileInfo(user_id);
		
		model.addAttribute("withdrawInfo", withdrawInfo);
		
		return "/views/mypage/myWithdraw";
	}
	
	// 비밀번호 체크
	@RequestMapping("/pwdChk")
	@ResponseBody
	public String checkPW(@AuthenticationPrincipal PrincipalDetail userDetail,
						  @RequestParam("inputPwd") String inputPwd) {
		String password = userDetail.getPassword();
		System.out.println("Controller password-> " + password);
		Boolean pwdBool = passwordEncoder.matches(inputPwd, password);
		System.out.println("Controller pwdBool-> " + pwdBool);
		if (pwdBool)	return "1";
		else			return "0";
	}
		
	// 회원 탈퇴 요청(프로세스)
	@RequestMapping("/commonUser/updateWithdraw")
	public String updateWithdraw(@AuthenticationPrincipal PrincipalDetail userDetail, Out outData, Model model) {
		System.out.println("MypageController updateWithdraw Start...");
		
		String user_id = userDetail.getUsername();
		// 탈퇴여부 Update
		int updateWithdraw = mypageService.updateWithdraw(user_id);
		System.out.println("SbController updateWithdraw->" + updateWithdraw);
		
		// 탈퇴회원 테이블(Out)에 Insert
		int putWithdraw = mypageService.putWithdraw(outData);
		System.out.println("MypageController putWithdraw-> " + putWithdraw);
		model.addAttribute("putWithdraw", putWithdraw);
		
		// 세션 로그아웃 되는 logic??
		
		
		return "/views/main";
	}
	
	// 찜 목록 화면 (틀)
	
	
	// 내가 남긴 문의(View)
	@RequestMapping("/commonUser/myQnA")
	public String getMyQna(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("MypageController getMyQnA Start...");
		
		String user_id = userDetail.getUsername();
		Qna getMyQna = mypageService.getMyQna(user_id);
		System.out.println("MypageController getMyQnA-> " + getMyQna);
		
		model.addAttribute("getMyQna", getMyQna);
		
		return "/views/mypage/myQnA";
	}
	
}





