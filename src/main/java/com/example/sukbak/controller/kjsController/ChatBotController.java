package com.example.sukbak.controller.kjsController;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sukbak.auth.PrincipalDetail;
import com.example.sukbak.model.Notice_Faq;
import com.example.sukbak.model.Qna;
import com.example.sukbak.service.kjsService.ChatBotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatBotController {
	private final ChatBotService cs;
	
	//notice 제목
	@PostMapping("/noticeFaqTitle")
	public List<Notice_Faq> noticeFaqtitle(String notice_type) {
    	List<Notice_Faq> noticeFaqtitle = cs.getNoticeFaqTitleList(notice_type);
    	
	    return noticeFaqtitle;
	}
	
	//notice 내용
	@PostMapping("/noticeFaqContent")
	public List<Notice_Faq> noticeFaqContent(String notice_id){
		List<Notice_Faq> noticeFaqContent = cs.getNoticeFaqContentList(notice_id);
		
		return noticeFaqContent;
	}
	
	//notice 삽입
	@PostMapping("/qnaInsert")
	public void qnaInsert(@AuthenticationPrincipal PrincipalDetail users, Qna qna) {
		String user_id = users.getUsername();
		qna.setUser_id(user_id);
		
		cs.qnaInsert(qna);
		
	}
}

