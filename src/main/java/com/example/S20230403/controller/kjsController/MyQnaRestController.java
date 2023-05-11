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
	
	@PostMapping("/commonUser/myQna")
	public List<Qna> myQna(@AuthenticationPrincipal PrincipalDetail userDetail, 
										String qna_type) {
		
		String user_id = userDetail.getUsername();
		
		List<Qna> myQna = ms.getMyQnaList(user_id, qna_type);
		
		return myQna;
	}
}
