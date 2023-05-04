package com.example.S20230403.controller.kjsController;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Qna;
import com.example.S20230403.service.kjsService.ChatBotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatBotController {
	private final ChatBotService cs;
	
	@PostMapping("/noticeFaqTitle")
	public List<Notice_Faq> noticeFaqtitle(String notice_type) {
		System.out.println("컨트롤러 notice_type->"+notice_type);
    	List<Notice_Faq> noticeFaqtitle = cs.getNoticeFaqTitleList(notice_type);
	    System.out.println("컨트롤러 noticeFaqtitle->"+noticeFaqtitle);
	    return noticeFaqtitle;
	}
	
	@PostMapping("/noticeFaqContent")
	public List<Notice_Faq> noticeFaqContent(String notice_id){
		System.out.println("notice_id->"+notice_id);
		List<Notice_Faq> noticeFaqContent = cs.getNoticeFaqContentList(notice_id);
		
		return noticeFaqContent;
	}
	
	@PostMapping("/qnaInsert")
	public void qnaInsert(@AuthenticationPrincipal PrincipalDetail users, Qna qna) {
		
		String user_id = users.getUsername();
		System.out.println("getUsername() ->" + user_id);
		
		qna.setUser_id(user_id);
		
		cs.qnaInsert(qna);
		
	}
}

