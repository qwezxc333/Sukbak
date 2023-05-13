package com.example.S20230403.controller.kjsController;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Qna;
import com.example.S20230403.service.kjsService.MyQnaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyQnaRestController {
	private final MyQnaService ms;
	
	//myQna 페이지로드용
	@RequestMapping("/commonUser/myQna")
	public String myQna() {
		return "/views/mypage/myQna";
	}
	
	//1:1문의, 불만접수 리스트
	@ResponseBody
	@RequestMapping("commonUser/myQnaAjax")
	public List<Qna> myQnaAjax(@AuthenticationPrincipal PrincipalDetail userDetail, 
							String qna_type){
		String user_id = userDetail.getUsername();
//		System.out.println("myQnaAjax user_id->" + user_id);
//		System.out.println("myQnaAjax qna_type->" + qna_type);
		
		List<Qna> myQnaAjax = ms.getMyQnaAjaxList(user_id, qna_type);
		
		return myQnaAjax;
	}
	
	// 문의 수정
	@ResponseBody
	@RequestMapping("/commonUser/myQnaUpdate")
	public void myQnaUpdate(Qna qna) {
//		System.out.println("qna업데이트 qnaTitle->"+ qna.getQna_title());
//		System.out.println("qna업데이트 qnaContent->"+ qna.getQna_content());
//		System.out.println("qna업데이트 qna_id->" + qna.getQna_id());
		ms.myQnaUpdate(qna);
	}
	
	// 문의 삭제
	@ResponseBody
	@RequestMapping("/commonUser/myQnaDelete")
	public void myQnaDelete(int qna_id) {
		System.out.println("qna_id--->" + qna_id);
		ms.myQnaDelete(qna_id);
	}
	
}
