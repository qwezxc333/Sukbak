package com.example.S20230403.controller.jjhController;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.JooJoin;
import com.example.S20230403.service.jjhService.AdminPageService;
import com.example.S20230403.service.jjhService.AdminPaging;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminPageController {
	
	private final AdminPageService as;
	
//	관리자 페이지에 일반 회원 테이블 불러오기 & 페이징
  @RequestMapping("/admin/adminPage-userlist")
  public String userlist(@AuthenticationPrincipal PrincipalDetail userDetail, 
			Model model, String currentPage, JooJoin jooJoin) {

	  String user_id = userDetail.getUsername();
	  model.addAttribute("user_id",user_id);
	  
     int userTotal = as.userTotal();
     AdminPaging page = new AdminPaging(userTotal, currentPage);
//     System.out.println("컨트롤러 userTotal-> "+userTotal);
//     System.out.println("컨트롤러 일반회원 시작 페이지-> "+ page.getStart());
//     System.out.println("컨트롤러 일반회원 끝 페이지-> "+ page.getEnd());
     
     jooJoin.setStart(page.getStart()); // 시작시 1
     jooJoin.setEnd(page.getEnd());     // 시작시 10
     
     List<JooJoin> userlist= as.userlist(jooJoin);
     
     model.addAttribute("userTotal", userTotal);
     model.addAttribute("userlist",userlist);
     model.addAttribute("page", page);
     
     return "views/admin/adminPage-userlist";
  }
	
//	관리자 페이지에 사업자 회원 테이블 불러오기 & 페이징
	@RequestMapping("/admin/adminPage-accomlist")
	public String accomlist(@AuthenticationPrincipal PrincipalDetail userDetail, 
			Model model, String currentPage, JooJoin jooJoin) {

		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
	  
		int accomTotal = as.accomTotal();
		AdminPaging page = new AdminPaging(accomTotal, currentPage);
//		System.out.println("컨트롤러 사업자 회원 -> "+currentPage+" 페이지");
//		System.out.println("컨트롤러 사업자 회원 시작 번호-> "+ page.getStart());
//		System.out.println("컨트롤러 사업자 회원 끝 번호-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> accomlist= as.accomlist(jooJoin);
		model.addAttribute("accomTotal", accomTotal);
		model.addAttribute("accomlist",accomlist);
		model.addAttribute("page", page);
		return "views/admin/adminPage-accomlist";
	}
	
//	관리자 페이지에 QnA 테이블 불러오기 & 페이징
	@RequestMapping("/admin/adminPage-qnalist")
	public String qnalist(@AuthenticationPrincipal PrincipalDetail userDetail, 
			Model model, String currentPage, JooJoin jooJoin) {

		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		  
		int qnaTotal = as.qnaTotal();
		AdminPaging page = new AdminPaging(qnaTotal, currentPage);
//		System.out.println("컨트롤러 게시판 -> "+currentPage+" 페이지");
//		System.out.println("컨트롤러 게시판 시작 번호-> "+ page.getStart());
//		System.out.println("컨트롤러 게시판 끝 번호-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> qnalist= as.qnalist(jooJoin);
		model.addAttribute("qnaTotal", qnaTotal);
		model.addAttribute("qnalist",qnalist);
		model.addAttribute("page", page);
		return "views/admin/adminPage-qnalist";
	}
	
//	관리자 페이지에 QnA 답변 테이블 불러오기 & 페이징
	@RequestMapping("/admin/adminPage-qnaRelist")
	public String qnaRelist(@AuthenticationPrincipal PrincipalDetail userDetail, 
			Model model, String currentPage, JooJoin jooJoin) {

		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		  
		int qnaTotal = as.qnaReTotal();
		AdminPaging page = new AdminPaging(qnaTotal, currentPage);
//		System.out.println("컨트롤러 게시판 -> "+currentPage+" 페이지");
//		System.out.println("컨트롤러 게시판 시작 번호-> "+ page.getStart());
//		System.out.println("컨트롤러 게시판 끝 번호-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> qnaRelist= as.qnaRelist(jooJoin);
		model.addAttribute("qnaTotal", qnaTotal);
		model.addAttribute("qnaRelist",qnaRelist);
		model.addAttribute("page", page);
		return "views/admin/adminPage-qnaRelist";
	}
	
//	관리자 페이지에 리뷰 테이블 불러오기 & 페이징
	@RequestMapping("/admin/adminPage-reviewlist")
	public String reviewlist(@AuthenticationPrincipal PrincipalDetail userDetail, 
			Model model, String currentPage, JooJoin jooJoin) {

		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		  
		int reviewTotal = as.reviewTotal();
		AdminPaging page = new AdminPaging(reviewTotal, currentPage);
//		System.out.println("컨트롤러 리뷰  -> "+currentPage+" 페이지");
//		System.out.println("컨트롤러 리뷰 시작 번호-> "+ page.getStart());
//		System.out.println("컨트롤러 리뷰 끝 번호-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
		
		List<JooJoin> reviewlist= as.reviewlist(jooJoin);
		model.addAttribute("reviewTotal", reviewTotal);
		model.addAttribute("reviewlist",reviewlist);
		model.addAttribute("page", page);
		return "views/admin/adminPage-reviewlist";
	}
	
//	관리자 페이지에 리뷰 삭제 관리 대상 데이터 불러오기 & 페이징
	@RequestMapping("/admin/adminPage-reviewDellist")
	public String reviewDellist(@AuthenticationPrincipal PrincipalDetail userDetail, 
			Model model, String currentPage, JooJoin jooJoin) {

		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		  
		int reviewTotal = as.reviewDelTotal();
		AdminPaging page = new AdminPaging(reviewTotal, currentPage);
//		System.out.println("컨트롤러 리뷰  -> "+currentPage+" 페이지");
//		System.out.println("컨트롤러 리뷰 시작 번호-> "+ page.getStart());
//		System.out.println("컨트롤러 리뷰 끝 번호-> "+ page.getEnd());
		
		jooJoin.setStart(page.getStart()); // 시작시 1
		jooJoin.setEnd(page.getEnd());     // 시작시 10
//		System.out.println("pay_id를 가져오는 지 확인: "+jooJoin.getPay_id());
		
		List<JooJoin> reviewDellist = as.reviewDellist(jooJoin);
		model.addAttribute("reviewTotal", reviewTotal);
		model.addAttribute("reviewDellist",reviewDellist);
		model.addAttribute("page", page);
//		model.addAttribute("jooJoin", jooJoin);
//		System.out.println("pay_id를 가져오는 지 확인22: "+jooJoin.getPay_id());
		return "views/admin/adminPage-reviewDellist";
	}
	
//	활성화 되어있는 일반 회원 -> 비활성화 시키는 로직
	@RequestMapping(value = "/admin/delUser")
	public String delUsers(@AuthenticationPrincipal PrincipalDetail userDetail, 
							JooJoin jooJoin, Model model) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
	  
//		System.out.println("jjhController AdminPageController delUsers start");
//		System.out.println("jjhController AdminPageController delUsers getusersid-> "
//							+jooJoin.getUser_id());
		int result = as.delUsers(jooJoin);
		return "forward:/admin/adminPage-userlist";
	}
	
//	사용가능(중분류 코드 : 210) 상태인 사업자(숙박업소) -> 사용 불가능 (230)으로 바꾸는 로직
	@RequestMapping(value = "/admin/delAccom")
	public String delAccom(@AuthenticationPrincipal PrincipalDetail userDetail, 
							JooJoin jooJoin, Model model) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		  
//		System.out.println("jjhController AdminPageController delAccom start");
//		System.out.println("jjhController AdminPageController delAccom getBiz_id-> "
//							+jooJoin.getBiz_id());
		int result = as.delAccom(jooJoin);
		return "forward:/admin/adminPage-accomlist";
	}
	
//	QnA 삭제
	@RequestMapping(value = "/admin/delQnA")
	public String delQnA(@AuthenticationPrincipal PrincipalDetail userDetail, 
						 @RequestParam("qna_id") int qna_id, Model model) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
			  
//		System.out.println("jjhController AdminPageController delQnA start");
		int result = as.delQnA(qna_id);
		return "forward:/admin/adminPage-qnalist";
	}
	
//	QnA 삭제
	@RequestMapping(value = "/admin/delQnAInRe")
	public String delQnAInRe(@AuthenticationPrincipal PrincipalDetail userDetail, 
			@RequestParam("qna_id") int qna_id, Model model) {
		
		String user_id = userDetail.getUsername();
		model.addAttribute("user_id",user_id);
		
//		System.out.println("jjhController AdminPageController delQnA start");
		int result = as.delQnA(qna_id);
		return "forward:/admin/adminPage-qnaRelist";
	}
	
//	QnA Reply 삭제
	@RequestMapping(value = "/admin/delQnARe")
	public String delQnARe(@AuthenticationPrincipal PrincipalDetail userDetail, 
			 			   @RequestParam("qna_id") int qna_id, Model model) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
 
//		System.out.println("jjhController AdminPageController delQnARe start");
		int result = as.delQnARe(qna_id);
		return "forward:/admin/adminPage-qnalist";
	}
	
//	Review 삭제
	@RequestMapping(value = "/admin/delReviewImg")
	public String delReviewImg(@AuthenticationPrincipal PrincipalDetail userDetail, 
							   @RequestParam("pay_id") int pay_id, Model model) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		
//		System.out.println("jjhController AdminPageController delReviewImg start");
		int result = as.delReviewImg(pay_id);
		return "forward:/admin/adminPage-reviewDellist";
	}
	
//	Review 삭제
	@RequestMapping(value = "/admin/delReImg")
	public String delReImg(@AuthenticationPrincipal PrincipalDetail userDetail, 
			@RequestParam("pay_id") int pay_id, Model model) {
		
		String user_id = userDetail.getUsername();
		model.addAttribute("user_id",user_id);
		
//		System.out.println("jjhController AdminPageController delReviewImg start");
		int result = as.delReviewImg(pay_id);
		return "forward:/admin/adminPage-reviewlist";
	}

//	Review 삭제 요청 반려
	@RequestMapping(value = "/admin/rejectDelReview")
	public String rejectDelReview(@AuthenticationPrincipal PrincipalDetail userDetail, 
									JooJoin jooJoin, Model model) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		  
//		System.out.println("jjhController AdminPageController rejectDelReview start");
		int result = as.rejectDelReview(jooJoin);
		return "forward:/admin/adminPage-reviewDellist";
	}
	
//	QnA 자세히 보기 for 답변 달기
	@RequestMapping(value = "/admin/detailQna")
	public String detailQna(@AuthenticationPrincipal PrincipalDetail userDetail, 
							int qna_id, Model model) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		
//		System.out.println("jjhController AdminPageController detailQna Start" );
//		System.out.println("jjhController AdminPageController detailQna qna_id-> "+qna_id );
		JooJoin jooJoin = as.detailQna(qna_id);
		model.addAttribute("qna_id",jooJoin.getQna_id());
		model.addAttribute("qna_type",jooJoin.getQna_type());
		model.addAttribute("user_id",jooJoin.getUser_id());
		model.addAttribute("qna_title",jooJoin.getQna_title());
		model.addAttribute("qna_content",jooJoin.getQna_content());
		model.addAttribute("qna_date",jooJoin.getQna_date());
		return "views/admin/adminPage-detailQna";
	}
	
//	답변 달기
	@RequestMapping(value = "/admin/saveReply")
	public String saveReply(@AuthenticationPrincipal PrincipalDetail userDetail, 
							Model model, JooJoin jooJoin, int qna_id) {
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		
//		System.out.println("jjhController AdminPageController saveReply start");
		int insertResult = as.saveReply(jooJoin);
		
		return "redirect:/admin/adminPage-qnaRelist";
	}
	
//	일반 회원 검색
	@GetMapping("/admin/adminPage-userSearchList")
	  public String userSearchList(@AuthenticationPrincipal PrincipalDetail userDetail, 
			  						Model model, String currentPage, JooJoin jooJoin) {
//		System.out.println("searchlist search -> "+jooJoin.getSearch());
		String search = jooJoin.getSearch();
		String keyword = jooJoin.getKeyword();
//		System.out.println("searchlist keyword -> "+jooJoin.getKeyword());
//		System.out.println("searchlist currentPage 확인 -> "+currentPage);
		
		String user_id = userDetail.getUsername();
	    model.addAttribute("user_id",user_id);
		
		 int userTotal = as.conditionUserTotal(jooJoin);
		 
	     AdminPaging page = new AdminPaging(userTotal, currentPage);
//	     System.out.println("컨트롤러 userSearchList-> "+userTotal);
//	     System.out.println("컨트롤러 일반회원 검색 시작 페이지-> "+ page.getStart());
//	     System.out.println("컨트롤러 일반회원 검색 끝 페이지-> "+ page.getEnd());
	     
	     jooJoin.setStart(page.getStart()); // 시작시 1
	     jooJoin.setEnd(page.getEnd());     // 시작시 10
	     
	     List<JooJoin> listSearchUser= as.listSearchUser(jooJoin);
	     
	     model.addAttribute("userTotal", userTotal);
	     model.addAttribute("userSearchList",listSearchUser);
	     model.addAttribute("page", page);
	     model.addAttribute("search", search);
	     model.addAttribute("keyword", keyword);
	     
	     return "views/admin/adminPage-userSearchList";
	  }
	
}
