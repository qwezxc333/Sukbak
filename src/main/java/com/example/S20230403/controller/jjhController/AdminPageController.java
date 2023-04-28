package com.example.S20230403.controller.jjhController;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.model.JooJoin;
import com.example.S20230403.service.jjhService.AdminPageService;
import com.example.S20230403.service.jjhService.AdminPaging;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminPageController {
	private final AdminPageService as;
//	관리자 페이지에 일반 회원, 사업자 회원, QnA, 리뷰, 이벤트, 공지사항 테이블 불러오기 & 페이징
	@GetMapping("adminPage")
	public String adminPage(Model model, String currentPage, JooJoin jooJoin) {
		int userTotal = as.userTotal();
		int accomTotal = as.accomTotal();
		int qnaTotal = as.qnaTotal();
		int reviewTotal = as.reviewTotal();
		
		AdminPaging page = new AdminPaging(userTotal, currentPage);
		AdminPaging page1 = new AdminPaging(accomTotal, currentPage);
		AdminPaging page2 = new AdminPaging(qnaTotal, currentPage);
		AdminPaging page3 = new AdminPaging(reviewTotal, currentPage);
		
		System.out.println("컨트롤러 userTotal-> "+userTotal);
		//	paging 작업
		System.out.println("컨트롤러 시작 page-> "+ page.getStart());
		System.out.println("컨트롤러 시작 끝 page-> "+ page.getEnd());
		
		// parameter emp -> page만 추가 setting
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> userlist= as.userlist(jooJoin);
		List<JooJoin> accomlist= as.accomlist(jooJoin);
		List<JooJoin> qnalist= as.qnalist(jooJoin);
		List<JooJoin> reviewlist= as.reviewlist(jooJoin);

		model.addAttribute("userTotal", userTotal);
		model.addAttribute("accomTotal", accomTotal);
		model.addAttribute("qnaTotal", qnaTotal);
		model.addAttribute("reviewTotal", reviewTotal);
		
		model.addAttribute("userlist",userlist);
		model.addAttribute("accomlist",accomlist);
		model.addAttribute("qnalist",qnalist);
		model.addAttribute("reviewlist",reviewlist);
		
		model.addAttribute("page", page);
		model.addAttribute("page1", page1);
		model.addAttribute("page2", page2);
		model.addAttribute("page3", page3);

		return "views/admin/adminPage";
	}
	
//	활성화 되어있는 일반 회원 -> 비활성화 시키는 로직
	@RequestMapping(value = "/delUser", method = RequestMethod.GET)
	public String delUsers(JooJoin jooJoin, Model model) {
		System.out.println("AdminController05 delUsers start");
		System.out.println("AdminController05 delUsers getusersid-> "+jooJoin.getUser_id());
		int result = as.delUsers(jooJoin);
		return "redirect:/adminPage";
	}
	
//	사용가능(중분류 코드 : 210) 상태인 사업자(숙박업소) -> 사용 불가능 (230)으로 바꾸는 로직
	@RequestMapping(value = "/delAccom", method = RequestMethod.GET)
	public String delAccom(JooJoin jooJoin, Model model) {
		System.out.println("AdminController05 delAccom start");
		System.out.println("AdminController05 delAccom getBiz_id-> "+jooJoin.getBiz_id());
		int result = as.delAccom(jooJoin);
		return "redirect:/adminPage";
	}
	
//	QnA 삭제
	@ResponseBody
	@RequestMapping(value = "/delQnA")
	public String delQnA(@RequestParam("qna_id") int qna_id, Model model) {
		System.out.println("AdminController05 delQnA start");
		int result = as.delQnA(qna_id);
		return "redirect:/qnalist";
	}
	
//	QnA Reply 삭제
	@ResponseBody
	@RequestMapping(value = "/delQnARe")
	public String delQnARe(@RequestParam("qna_id") int qna_id, Model model) {
		System.out.println("AdminController05 delQnARe start");
		int result = as.delQnARe(qna_id);
		return "redirect:/qnalist";
	}
	
//	Review 삭제
	@ResponseBody
	@RequestMapping(value = "/delReview")
	public String delReview(@RequestParam("pay_id") int pay_id, Model model) {
		System.out.println("AdminController05 delReview start");
		int result = as.delReview(pay_id);
		return "redirect:/reviewlist";
	}
	
	@PostMapping(value = "detailQna")
	public String detailQna(int qna_id, Model model) {
		System.out.println("AdminController05 detailQna Start" );
		System.out.println("AdminController05 detailQna qna_id-> "+qna_id );
		JooJoin jooJoin = as.detailQna(qna_id);
//		model.addAttribute("qna_id",jooJoin.getQna_id());
		model.addAttribute("qna_type",jooJoin.getQna_type());
		model.addAttribute("user_id",jooJoin.getUser_id());
		model.addAttribute("qna_title",jooJoin.getQna_title());
		model.addAttribute("qna_content",jooJoin.getQna_content());
		return "views/admin/detailQna";
	}
	
//	답변 달기
	@PostMapping(value = "/saveReply")
	public String saveReply(Model model, JooJoin jooJoin, int qna_id) {
		System.out.println("AdminController05 saveReply start");
		int insertResult = as.saveReply(jooJoin);
		
		if(insertResult > 0) return "forward:/adminPage";
		else {
			return "redirect:/adminPage";
		}
	}
	
//	@GetMapping("/admin")
//	public String adminPageView(JooJoin auth_level) {
//		if(auth_level != null) {
//			return "views/adminPage";
//		}
//		
//		return "views/admin/login";
//	}
	
}
