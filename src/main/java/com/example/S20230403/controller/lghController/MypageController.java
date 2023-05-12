package com.example.S20230403.controller.lghController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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


	@Value("${file.dir}")
	private String fileDir;

	//	=============== 프로필 관련 ===============
	// 내 프로필 화면 조회(View)
	@RequestMapping("/commonUser/myProfile")
	public String getMyProfile(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		System.out.println("MypageController getMyProfile start");
		String user_id = userDetail.getUsername();
		System.out.println("MypageController 프로필 정보 불러오기 user_id-> " + user_id);
		Users myProfileInfo = mypageService.getMyProfileInfo(user_id);
		// mapper로 가져온 결과 조회
		System.out.println("MypageController 프로필 정보 myProfileInfo-> " + myProfileInfo);
		model.addAttribute("myProfileInfo", myProfileInfo);

		return "/views/mypage/myProfile";
	}

	// 내 프로필 수정(프로세스)
	@PostMapping("/commonUser/updateMyProfile")
	public String updateMyProfile(@AuthenticationPrincipal PrincipalDetail userDetail, Users users) {
		//System.out.println("MypageController updateMyProfile Start...");
		users.setUser_id(userDetail.getUsername());
		int updateMyProfile = mypageService.updateMyProfile(users);
		// update 성공시 result 1 반환
		//System.out.println("MypageController updateMyProfile result-> " + updateMyProfile);

		return "redirect:/commonUser/myProfile";
	}

	// 비밀번호 수정(View)
	@RequestMapping("/commonUser/myPwChange")
	public String getMyPwCheck(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		//System.out.println("MypageController myPwChange Start...");
		String user_id = userDetail.getUsername();
		//System.out.println("MypageController myPwChange user_id-> " + user_id);
		//System.out.println("MypageController myPwChange password-> " + userDetail.getPassword());
		Users myPassword = mypageService.getMyProfileInfo(user_id);
		model.addAttribute("myPassword", myPassword);

		return "/views/mypage/myPwChange";
	}


	// 비밀번호 체크
	@RequestMapping("/pwdChk")
	@ResponseBody
	public String checkPW(@AuthenticationPrincipal PrincipalDetail userDetail,
			@RequestParam("inputPwd") String inputPwd) {
		String password = userDetail.getPassword();
		//System.out.println("Controller password-> " + password);
		Boolean pwdBool = passwordEncoder.matches(inputPwd, password);
		//System.out.println("Controller pwdBool-> " + pwdBool);
		if (pwdBool)
			return "1";
		else
			return "0";
	}

	// 비밀번호 수정(프로세스)
	@RequestMapping("/commonUser/updateMyPassword")
	public String updatePassword(@AuthenticationPrincipal PrincipalDetail userDetail, Users users,
			@RequestParam("newPwd") String newPwd, HttpSession session) {
		//System.out.println("MypageController updatePassword start...");
		//System.out.println("newPwdBefore-> " + newPwd);
		String encodedPwd = new BCryptPasswordEncoder().encode(newPwd);
		//System.out.println("newPwdAfter-> " + encodedPwd);
		users.setPassword(encodedPwd);
		users.setUser_id(userDetail.getUsername());
		int updatePwd = mypageService.updatePassword(users);
		//System.out.println("MypageController updatePassword result-> " + updatePwd);

		// 보안 정책상 처음 로그인된 ID와 PW 정보로 session이 유지되기때문에 로그아웃 후 업데이트 된 정보로 재로그인 필요.
		session.invalidate();

		return "redirect:/";
	}



	//  =============== 예약 관련 ===============
	// 예약 내역 조회(View)
	@RequestMapping("/commonUser/myReserved")
	public String getMyResvLists(@AuthenticationPrincipal PrincipalDetail userDetail, GunJoin gunJoin, Model model) {
		//System.out.println("MypageController getMyResvLists Start...");

		// 예약 정보 불러오기
		String user_id = userDetail.getUsername();
		//System.out.println("MypageController getMyResvLists user_id-> " + user_id);
		List<GunJoin> myResvList = mypageService.getMyResvList(user_id);
		//System.out.println("MypageController list getMyResvList.size()-> " + myResvList.size());

		// 예약 내역의 후기 작성여부 확인
		gunJoin.setUser_id(user_id);
		Review myResvReviews = mypageService.getMyResvReviews(gunJoin);
		//System.out.println("MypageController list getMyResvReviews-> " + myResvReviews);

		model.addAttribute("myResvList", myResvList);
		model.addAttribute("myResvReviews", myResvReviews);

		return "/views/mypage/myReserved";
	}

	// 예약 취소하기(프로세스)
	@RequestMapping("/commonUser/cancelMyResv")
	public String cancelMyResv(@AuthenticationPrincipal PrincipalDetail userDetail, Model model,
			String biz_id, int resv_id, int r_id, int pay_id, GunJoin gunJoin) {
		//System.out.println("MypageController cancelMyResv Start...");

		// soldout, payment 삭제 후 --> reservation 삭제
		gunJoin.setBiz_id(biz_id);
		gunJoin.setResv_id(resv_id);
		gunJoin.setR_id(r_id);
		gunJoin.setPay_id(pay_id);
		mypageService.cancelResv(gunJoin);

		return "redirect:/commonUser/myReserved";
	}



	//  =============== 리뷰 관련 ===============
	// 신규 후기 작성 페이지(View)
	@RequestMapping("/commonUser/myNewReview")
	public String getMyNewReview(@AuthenticationPrincipal PrincipalDetail userDetail,
			@RequestParam("biz_id") String biz_id, @RequestParam("r_id") int r_id,
			Room room, Review review, Review_Img revImg, Model model) {
		System.out.println("MypageController getMyNewReview Start...");

		// 사용자 닉네임 가져오기
		String user_id = userDetail.getUsername();
		Users myNickname = mypageService.getMyProfileInfo(user_id);
		System.out.println("room.getBiz_id  ="+room.getBiz_id());
		System.out.println("room.getBiz_name()) ==>"+room.getBiz_name());

		// 숙박업체 정보 가져오기
		GunJoin myAccomInfo = mypageService.getMyAccomInfo(room);
		System.out.println(myAccomInfo.getBiz_name());
		model.addAttribute("myNickname", myNickname);
		model.addAttribute("myAccomInfo", myAccomInfo);
		model.addAttribute("revImg", revImg);
		model.addAttribute("pay_id", review.getPay_id());

		return "/views/mypage/myNewReview";
	}

	// 새 후기 작성하기(프로세스)
	@RequestMapping("/commonUser/putReview")
	public String putMyReview(GunJoin gj, Model model, @RequestParam MultipartFile[] files,
			Review review, Review_Img revImg) throws Exception {
		//System.out.println("1. MypageController putMyReview Start...");
		//System.out.println("1. MypageController putMyReview Start... 메타경로 -> "+fileDir);

		// Review 테이블에 Insert
		int rating = gj.getRating();
		//System.out.println("별점 점수-> " + rating);
		int putMyReview = mypageService.putMyReview(gj);
		//System.out.println("2. MypageController putMyReview result-> " + putMyReview);

		//	 	 ---메타데이터 경로 구하는 법.
		// 	    이미지 업로드(메타데이터로 경로 지정, jsp사용할 때도 이렇게 잡을 것. getContextPath()는 옛날방식.) 
		// 1. private final ResourceLoader resourceLoader; 빈 선언 
		// 2. Resource resource = resourceLoader.getResource("classpath:static/img/review");
		//    2번이 실질적 메타데이터 경로
		// 3. String metaPath = resource.getURL().getPath() + File.separator;
		//		resource.getURL().getPath()는 해당 리소스의 URL을 가져와서 그 URL의 경로를 반환
		//      .getPath() 메서드를 호출하면 URL의 경로 부분이 반환되며, 이는 해당 리소스의 메타데이터 경로를 나타냄
		// 4. metaPath -> C:\Users\\user\git\S20230403\bin\main\static\img\review\ 나온 값을 yml에 주입함.
		// 5. yml에 주입한 값을 @Value 어노테이션으로 컨트롤러에 선언, String 변수로 받는다. (fileDir)

		String extension = null;
		String savedName = null;
		File convertFile = null;

		//String rootPath = ("C:\\Users\\user\\git\\S20230403\\bin\\main\\static");
		String rootPath = fileDir.substring(0, fileDir.lastIndexOf("\\img\\review\\"));
		//System.out.println("찬규 자른 루트경로 경로-> \\img\\review\\ 이거나오면안됨"+rootPath);

		for (MultipartFile file : files) {
			if (file != null && !file.isEmpty()) {
				extension = FilenameUtils.getExtension(file.getOriginalFilename());
				//System.out.println("OriginalFilename extension-> " + extension);
				savedName = uploadFile(file.getBytes(), fileDir, revImg, extension);

				// rootPath = C:\Users\\user\git\S20230403\bin\main\static
				// savedName = \img\review\56-1.png (DB에 저장되는 이름.)
				convertFile = new File(rootPath + savedName);
				//System.out.println("최종 저장경로-> "+convertFile);
				file.transferTo(convertFile);
			} else {
				//System.out.println("선택된 파일이 없습니다.");
			}
		}

		if (putMyReview > 0) {
			return "redirect:/commonUser/myReviews";
		} else {
			model.addAttribute("msg", "후기 입력에 실패했습니다.");
			return "forward:/commonUser/myNewReview";
		}

	}

	// savedName(파일명) 정의 및 DB에 입력
	private String uploadFile(byte[] fileData, String fileDir, Review_Img revImg, String extension) throws IOException {
		//System.out.println("5. MypageController uploadFile start...");	
		// View에서 이미지 출력시 '\img\review\' 부분 하드코딩하지 않도록 (보안 문제)
		// 해당 경로 별도로 추출하여 String타입 변수(extractedPath)로 선언
		String extractedPath = fileDir.substring(fileDir.lastIndexOf("\\img\\review\\"));
		//System.out.println("자른 경로 -> "+extractedPath);

		// Review_img_ID(MAX) 키 정의
		// 현재 작성중인 Review의 pay_id와 일치하는 review값 중 MAX Review_img_id 값을 가져와 거기에 1을 더한다.
		int imgNum = mypageService.getMaxImgNum(revImg);
		imgNum += 1;

		//System.out.println("6.MypageController uploadFile imgNum-> " + imgNum);
		// 파일명 형식: (extractedPath)(pay_id)-(review_img_id).(확장자명)--> 예) \img\review\26-1.jpg
		String savedName = extractedPath + revImg.getPay_id() + "-" + imgNum + "." + extension;
		//System.out.println("7.MypageController uploadFile savedName-> " + savedName);
		// 지정한 경로(fileDir)에 동명의 폴더가 존재하지 않을시 Directory 생성
		// Directory 생성
		File fileDirectory = new File(fileDir);
		if (!fileDirectory.exists()) {
			// 신규 폴더(Directory) 생성
			fileDirectory.mkdirs();
			//System.out.println("8.업로드용 폴더 생성 : " + fileDir);
		}

		//System.out.println("10.MypageController uploadFile savedName->" + savedName);

		// 위에서 정의한 Review_img_ID와 파일명으로 데이터 setting
		revImg.setReview_img_id(imgNum);
		revImg.setReview_img(savedName);
		//System.out.println("11.MypageController uploadFile revImg-> " + revImg);

		// Review_img 테이블에 setting한 정보 Insert
		int putRevImgNum = mypageService.putRevImgNum(revImg);
		//System.out.println("12. revImgControlelr putRevImg result-> " + putRevImgNum);

		return savedName;
	}



	// 작성한 후기 조회(View)
	@RequestMapping("/commonUser/myReviews")
	public String getMyReviews(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) throws IOException {
		//System.out.println("MypageController getMyReviews Start...");
		String user_id = userDetail.getUsername();

		// 리뷰 테이블의 데이터(이미지 포함) 불러오기
		List<Review> myReviewImgList = mypageService.getMyReviewImages(user_id);
		//System.out.println("MypageController myReviewImgList-> " + myReviewImgList);
		model.addAttribute("myReviewImgList", myReviewImgList);

		return "/views/mypage/myReviews";
	}

	// 작성한 후기 삭제(프로세스)
	@RequestMapping("/commonUser/deleteMyReview")
	public String deleteMyReview(@AuthenticationPrincipal PrincipalDetail userDetail, Review review) {
		//System.out.println("MypageController deleteMyReview Start...");
		String rootPath = fileDir.substring(0, fileDir.lastIndexOf("\\img\\review\\"));
		//System.out.println("찬규 자른 루트경로  경로-> \\img\\review\\ 이거나오면안됨"+rootPath);

		Review_Img delImgNums = new Review_Img();
		delImgNums.setPay_id(review.getPay_id());
		List<Review_Img> delImgList = mypageService.getDelImgList(delImgNums);

		for (Review_Img revImg : delImgList) {
			String rev_img = revImg.getReview_img();
			File convertFile = new File(rootPath + rev_img);
			//System.out.println("삭제할 때 화긴 -> "+convertFile);
			String deleteImg = convertFile.toString();
			File file = new File(deleteImg);
			file.delete();
		}

		int pay_id = review.getPay_id();
		int deleteMyReview = mypageService.deleteMyReview(pay_id);
		//System.out.println("MypageController deleteMyReview delRev-> " + deleteMyReview);

		return "redirect:/commonUser/myReviews";
	}



	//  =============== 탈퇴 관련 ===============
	// 회원 탈퇴 화면(View)
	@RequestMapping("/commonUser/myWithdraw")
	public String getWithdrawInfo(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		//System.out.println("MypageController getWithdrawInfo Start...");
		String user_id = userDetail.getUsername();
		Users withdrawInfo = mypageService.getMyProfileInfo(user_id);
		model.addAttribute("withdrawInfo", withdrawInfo);

		return "/views/mypage/myWithdraw";
	}

	// 회원 탈퇴 요청(프로세스)
	@RequestMapping("/commonUser/updateWithdraw")
	public String updateWithdraw(@AuthenticationPrincipal PrincipalDetail userDetail, Out outData,
			Model model, HttpSession session) {
		//System.out.println("MypageController updateWithdraw Start...");
		String user_id = userDetail.getUsername();

		// 탈퇴여부 Update(User 테이블에서 deactivated로 변경)
		mypageService.updateWithdraw(user_id);

		// 탈퇴회원 테이블(Out)에 Insert
		mypageService.putWithdraw(outData);

		// 세션 로그아웃
		session.invalidate();

		return "redirect:/";
	}

}
