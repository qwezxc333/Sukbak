package com.example.S20230403.controller.ljyController;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Out;
import com.example.S20230403.model.Users;
import com.example.S20230403.service.lghService.MypageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class bizUserProfileController {
	
	private final MypageService mypageService;
	private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	@Value("${file.dir}")
	private String fileDir;

	
//	=============== 프로필 관련 ===============
	// 내 프로필 화면 조회(View)
	@RequestMapping("/biz/bizProfile")
	public String getBizProfile(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		////System.out.println("MypageController getMyProfile start");
		String user_id = userDetail.getUsername();
		////System.out.println("MypageController 프로필 정보 불러오기 user_id-> " + user_id);
		Users myProfileInfo = mypageService.getMyProfileInfo(user_id);
		// mapper로 가져온 결과 조회
		////System.out.println("MypageController 프로필 정보 myProfileInfo-> " + myProfileInfo);
		model.addAttribute("myProfileInfo", myProfileInfo);
		
		return "/views/biz/bizProfile";
	}

	// 내 프로필 수정(프로세스)
	@PostMapping("/biz/updateMyProfile")
	public String updateMyProfile(@AuthenticationPrincipal PrincipalDetail userDetail, Users users) {
		////System.out.println("MypageController updateMyProfile Start...");
		users.setUser_id(userDetail.getUsername());
		int updateMyProfile = mypageService.updateMyProfile(users);
		// update 성공시 result 1 반환
		////System.out.println("MypageController updateMyProfile result-> " + updateMyProfile);

		return "redirect:/biz/bizProfile";
	}

	// 비밀번호 수정(View)
	@RequestMapping("/biz/bizPwChange")
	public String getMyPwCheck(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		////System.out.println("MypageController myPwChange Start...");
		String user_id = userDetail.getUsername();
		////System.out.println("MypageController myPwChange user_id-> " + user_id);
		////System.out.println("MypageController myPwChange password-> " + userDetail.getPassword());
		Users myPassword = mypageService.getMyProfileInfo(user_id);
		model.addAttribute("myPassword", myPassword);

		return "/views/biz/bizPwChange";
	}
	

	// 비밀번호 체크
	@RequestMapping("/bizPwdChk") 
	@ResponseBody
	public String checkPW(@AuthenticationPrincipal PrincipalDetail userDetail,
						  @RequestParam("inputPwd") String inputPwd) {
		String password = userDetail.getPassword();
		////System.out.println("Controller password-> " + password);
		Boolean pwdBool = passwordEncoder.matches(inputPwd, password);
		////System.out.println("Controller pwdBool-> " + pwdBool);
		if (pwdBool)
			return "1";
		else
			return "0";
	}

	// 비밀번호 수정(프로세스)
	@RequestMapping("/biz/updateMyPassword")
	public String updatePassword(@AuthenticationPrincipal PrincipalDetail userDetail, Users users,
								 @RequestParam("newPwd") String newPwd, HttpSession session) {
		////System.out.println("MypageController updatePassword start...");
		////System.out.println("newPwdBefore-> " + newPwd);
		String encodedPwd = new BCryptPasswordEncoder().encode(newPwd);
		////System.out.println("newPwdAfter-> " + encodedPwd);
		users.setPassword(encodedPwd);
		users.setUser_id(userDetail.getUsername());
		int updatePwd = mypageService.updatePassword(users);
		////System.out.println("MypageController updatePassword result-> " + updatePwd);
		
		// 보안 정책상 처음 로그인된 ID와 PW 정보로 session이 유지되기때문에 로그아웃 후 업데이트 된 정보로 재로그인 필요.
		session.invalidate();

		return "redirect:/";
	}

//  =============== 탈퇴 관련 ===============
	// 회원 탈퇴 화면(View)
	@RequestMapping("/biz/bizWithdraw")
	public String getWithdrawInfo(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		////System.out.println("MypageController getWithdrawInfo Start...");
		String user_id = userDetail.getUsername();
		Users withdrawInfo = mypageService.getMyProfileInfo(user_id);
		model.addAttribute("withdrawInfo", withdrawInfo);

		return "/views/biz/bizWithdraw";
	}	
	
	// 회원 탈퇴 요청(프로세스)
	@RequestMapping("/biz/updateWithdraw")
	public String updateBizWithdraw(@AuthenticationPrincipal PrincipalDetail userDetail, Out outData,
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
