package com.example.S20230403.controller.jjhController;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.S20230403.model.JooJoin;
import com.example.S20230403.service.jjhService.AdminPageService;
import com.example.S20230403.service.jjhService.AdminPaging;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminPageController {
	
	private final AdminPageService as;
	
//	관리자 페이지에 일반 회원 테이블 불러오기 & 페이징
	@RequestMapping("/adminPage")
	public String adminPage(Model model, String currentPage, JooJoin jooJoin) {
		int userTotal = as.userTotal();
		AdminPaging page = new AdminPaging(userTotal, currentPage);
		System.out.println("컨트롤러 userTotal-> "+userTotal);
		System.out.println("컨트롤러 일반회원 시작 페이지-> "+ page.getStart());
		System.out.println("컨트롤러 일반회원 끝 페이지-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> userlist= as.userlist(jooJoin);

		model.addAttribute("userTotal", userTotal);
		model.addAttribute("userlist",userlist);
		model.addAttribute("page", page);
		return "views/admin/adminPage";
	}
	
//	관리자 페이지에 사업자 회원 테이블 불러오기 & 페이징
	@RequestMapping("/adminPage-accomlist")
	public String accomlist(Model model, String currentPage, JooJoin jooJoin) {
		int accomTotal = as.accomTotal();
		AdminPaging page = new AdminPaging(accomTotal, currentPage);
		System.out.println("컨트롤러 accomTotal-> "+accomTotal);
		System.out.println("컨트롤러 사업자 회원 시작 페이지-> "+ page.getStart());
		System.out.println("컨트롤러 사업자 회원 끝 페이지-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> accomlist= as.accomlist(jooJoin);
		model.addAttribute("accomTotal", accomTotal);
		model.addAttribute("accomlist",accomlist);
		model.addAttribute("page", page);
		return "views/admin/adminPage-accomlist";
	}
	
//	관리자 페이지에 QnA 테이블 불러오기 & 페이징
	@RequestMapping("/adminPage-qnalist")
	public String qnalist(Model model, String currentPage, JooJoin jooJoin) {
		int qnaTotal = as.qnaTotal();
		AdminPaging page = new AdminPaging(qnaTotal, currentPage);
		System.out.println("컨트롤러 qnaTotal-> "+qnaTotal);
		System.out.println("컨트롤러 게시판 시작 페이지-> "+ page.getStart());
		System.out.println("컨트롤러 게시판 끝 페이지-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> qnalist= as.qnalist(jooJoin);
		model.addAttribute("qnaTotal", qnaTotal);
		model.addAttribute("qnalist",qnalist);
		model.addAttribute("page", page);
		return "views/admin/adminPage-qnalist";
	}
	
//	관리자 페이지에 리뷰 테이블 불러오기 & 페이징
	@RequestMapping("/adminPage-reviewlist")
	public String reviewlist(Model model, String currentPage, JooJoin jooJoin) {
		int reviewTotal = as.reviewTotal();
		AdminPaging page = new AdminPaging(reviewTotal, currentPage);
		System.out.println("컨트롤러 reviewTotal-> "+reviewTotal);
		System.out.println("컨트롤러 리뷰 시작 페이지-> "+ page.getStart());
		System.out.println("컨트롤러 리뷰 끝 페이지-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> reviewlist= as.reviewlist(jooJoin);
		model.addAttribute("reviewTotal", reviewTotal);
		model.addAttribute("reviewlist",reviewlist);
		model.addAttribute("page", page);
		return "views/admin/adminPage-reviewlist";
	}
	
//	활성화 되어있는 일반 회원 -> 비활성화 시키는 로직
	@RequestMapping(value = "/delUser")
	public String delUsers(JooJoin jooJoin, Model model) {
		System.out.println("jjhController AdminPageController delUsers start");
		System.out.println("jjhController AdminPageController delUsers getusersid-> "+jooJoin.getUser_id());
		int result = as.delUsers(jooJoin);
		return "forward:/adminPage";
	}
	
//	사용가능(중분류 코드 : 210) 상태인 사업자(숙박업소) -> 사용 불가능 (230)으로 바꾸는 로직
	@RequestMapping(value = "/delAccom")
	public String delAccom(JooJoin jooJoin, Model model) {
		System.out.println("jjhController AdminPageController delAccom start");
		System.out.println("jjhController AdminPageController delAccom getBiz_id-> "+jooJoin.getBiz_id());
		int result = as.delAccom(jooJoin);
		return "forward:/adminPage-accomlist";
	}
	
//	QnA 삭제
	@RequestMapping(value = "/delQnA")
	public String delQnA(@RequestParam("qna_id") int qna_id, Model model) {
		System.out.println("jjhController AdminPageController delQnA start");
		int result = as.delQnA(qna_id);
		return "forward:/adminPage-qnalist";
	}
	
//	QnA Reply 삭제
	@RequestMapping(value = "/delQnARe")
	public String delQnARe(@RequestParam("qna_id") int qna_id, Model model) {
		System.out.println("jjhController AdminPageController delQnARe start");
		int result = as.delQnARe(qna_id);
		return "forward:/adminPage-qnalist";
	}
	
//	Review 삭제
	@RequestMapping(value = "/delReviewImg")
	public String delReviewImg(@RequestParam("pay_id") int pay_id, Model model) {
		System.out.println("jjhController AdminPageController delReviewImg start");
		int result = as.delReviewImg(pay_id);
		return "forward:/adminPage-reviewlist";
	}

//	Review 삭제 요청 반려
	@RequestMapping(value = "/rejectDelReview")
	public String rejectDelReview(JooJoin jooJoin, Model model) {
			System.out.println("jjhController AdminPageController rejectDelReview start");
			int result = as.rejectDelReview(jooJoin);
			return "forward:/adminPage-reviewlist";
	}
	
//	QnA 자세히 보기 for 답변 달기
	@RequestMapping(value = "/detailQna")
	public String detailQna(int qna_id, Model model) {
		System.out.println("jjhController AdminPageController detailQna Start" );
		System.out.println("jjhController AdminPageController detailQna qna_id-> "+qna_id );
		JooJoin jooJoin = as.detailQna(qna_id);
		model.addAttribute("qna_id",jooJoin.getQna_id());
		model.addAttribute("qna_type",jooJoin.getQna_type());
		model.addAttribute("user_id",jooJoin.getUser_id());
		model.addAttribute("qna_title",jooJoin.getQna_title());
		model.addAttribute("qna_content",jooJoin.getQna_content());
		model.addAttribute("write_time",jooJoin.getWrite_time());
		return "views/admin/detailQna";
	}
	
//	답변 달기
	@RequestMapping(value = "/saveReply")
	public String saveReply(Model model, JooJoin jooJoin, int qna_id) {
		System.out.println("jjhController AdminPageController saveReply start");
		int insertResult = as.saveReply(jooJoin);
		
		if(insertResult > 0) return "forward:/adminPage-qnalist";
		else {
			return "redirect:/adminPage-qnalist";
		}
	}
	
//	@RequestMapping("userSeacrh1")
//	public String userSeacrh1(JooJoin users, Model model, String currentPage) {
//		int totalUser = as.conditionUserCount(users);
//		AdminPaging page =new AdminPaging(totalUser, currentPage);
//		
//		users.setStart(page.getStart());
//		users.setEnd(page.getEnd());
//		
//		List<JooJoin> listSearchUser = as.listSearchUsers(users);
//		
//		model.addAttribute("userTotal",totalUser);
//		model.addAttribute("page",page);
//		model.addAttribute("userlist",listSearchUser);
//		return "/views/admin/adminPage";
//		
//	}
	
}
