package com.example.S20230403.controller.kjsController;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Qna;
import com.example.S20230403.service.kjsService.MyQnaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyQnaRestController {
	private final MyQnaService ms;
	
	//qna 리스트
	@PostMapping("/commonUser/myQna")
	public List<Qna> myQna(@AuthenticationPrincipal PrincipalDetail userDetail, 
										String qna_type) {
		
		String user_id = userDetail.getUsername();
		
		List<Qna> myQna = ms.getMyQnaList(user_id, qna_type);
		
		return myQna;
	}
	
	//qna 수정
	@PostMapping("/commonUser/myQnaUpdate")
	public void myQnaUpdate(Qna qna) {
		System.out.println("qna업데이트 qnaTitle->"+ qna.getQna_title());
		System.out.println("qna업데이트 qnaContent->"+ qna.getQna_content());
		System.out.println("qna업데이트 qna_id->" + qna.getQna_id());
		ms.myQnaUpdate(qna);
	}
	
	//qna 삭제
	@PostMapping("/commonUser/myQnaDelete")
	public void myQnaDelete(int qna_id) {
		System.out.println("qna_id--->" + qna_id);
		ms.myQnaDelete(qna_id);
	}
	
}
