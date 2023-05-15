package com.example.S20230403.controller.lysController;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.model.Event;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Qna;
import com.example.S20230403.service.lysService.NotiServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
@RequiredArgsConstructor
public class NotiController {
	private final NotiServiceImpl notiServiceImpl;
	
	@GetMapping("noti")
	public String noti(Model model,Notice_Faq nf, Event event) {
		//system.out.println("Qna Start Controller");
	
		List<Notice_Faq> nfqlist =notiServiceImpl.listNoticeFaq(nf);
		List<Notice_Faq> listnc =notiServiceImpl.listnc(nf);
		List<Notice_Faq> listnt =notiServiceImpl.listnt(nf);
		List<Event> sukbakEvents = notiServiceImpl.getEvent(nf);
		/*
		 * 
		 * //system.out.println("이벤트 사이즈 -> "+sukbakEvents.size());
		 * 
		 */
		model.addAttribute("sukbakEvents", sukbakEvents);
		model.addAttribute("nfqlist",nfqlist);
		model.addAttribute("listnc",listnc);
		model.addAttribute("listnt",listnt);
		

		return "/views/noti";
	}
	//qna를 입력했을때 주희쪽에 확인이 되는지 보려함
	@ResponseBody
	@PostMapping("/QnASend")
	public int qnaInsert(Model model,Qna qna ) {
		qna.setUser_id(qna.getUser_id());
		qna.setQna_type(qna.getQna_type());
		qna.setQna_title(qna.getQna_title());
		qna.setQna_content(qna.getQna_content());
		//system.out.println("qna.getUser_id() =>"+qna.getUser_id());
		//system.out.println("qna.getQna_type() => "+qna.getQna_type());
		//system.out.println("qna.getQna_title() =>"+qna.getQna_title());
		//system.out.println("qna.getQna_content() =>"+qna.getQna_content());
		
		
		int qnaInsert =notiServiceImpl.qnaInset(qna);
		
		
		return qnaInsert;
	}
}
