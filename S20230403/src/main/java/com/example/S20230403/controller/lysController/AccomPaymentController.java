package com.example.S20230403.controller.lysController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Soldout;
import com.example.S20230403.model.Users;
import com.example.S20230403.service.lysService.Paging;
import com.example.S20230403.service.lysService.Service06;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AccomPaymentController {

	private final Service06 s06;

	//결제쪽
	@GetMapping("/payment")
	public String r_name(Model model, AccomPayment apt, String checkIn, String checkOut) {
		//List<Payment> r_name1=s06.getr_name(pmt);
		List<AccomPayment> getApt = s06.getapt(apt);
		List<Room> r_price=s06.getR_price(apt);
		apt.setBiz_id(apt.getBiz_id());
		apt.setCheck_in(checkIn);
		apt.setCheck_out(checkOut);
		System.out.println("첵인 첵아웃 ==>    "+checkIn+checkOut);


		String dateStr1 = checkIn; // 첫 번째 날짜 문자열
		String dateStr2 = checkOut; // 두 번째 날짜 문자열

		LocalDate date1 = LocalDate.parse(dateStr1); // 첫 번째 날짜 문자열을 LocalDate 객체로 변환
		LocalDate date2 = LocalDate.parse(dateStr2); // 두 번째 날짜 문자열을 LocalDate 객체로 변환

		long daysBetween = ChronoUnit.DAYS.between(date1, date2); // 두 날짜 사이의 일자 차이 계산

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E)"); // 날짜 포맷팅을 위한 DateTimeFormatter 생성
		String formattedDate1 = date1.format(formatter); // 첫 번째 날짜를 포맷팅
		String formattedDate2 = date2.format(formatter); // 두 번째 날짜를 포맷팅

		System.out.println("첫 번째 날짜: " + formattedDate1); // 첫 번째 날짜 출력
		System.out.println("두 번째 날짜: " + formattedDate2); // 두 번째 날짜 출력
		System.out.println("일자 차이: " + daysBetween + "일"); // 일자 차이 출력



		if (!r_price.isEmpty()) {
			Room r_price1 =r_price.get(0);
			apt.setR_price(r_price1.getR_price());
			apt.setR_name(r_price1.getR_name());

		}
		if (!getApt.isEmpty()) { // 리스트가 비어있지 않은 경우에만 처리
			AccomPayment payment = getApt.get(0); // 첫 번째 AccomPayment 객체 가져오기
			apt.setBiz_name(payment.getBiz_name());
			apt.setR_capacity(payment.getR_capacity());
		}

		model.addAttribute("formattedDate1",formattedDate1);
		model.addAttribute("formattedDate2",formattedDate2);
		model.addAttribute("daysBetween",daysBetween);
		model.addAttribute("pmt", apt);
		System.out.println("pmt =>" + apt);

		return "/views/payment";
	}
	
	@ResponseBody
	@GetMapping("/ResvDate")
	public List<Object> getResvDate(AccomPayment apt){
	    System.out.println("체크인 가지고 와줘"+ apt.getCheck_in());
	    System.out.println("체크아웃 가지고 와줘"+ apt.getCheck_out());
	    List<Soldout> getResvDate = s06.getResvDate(apt);

	    List<Object> combinedList = new ArrayList<>();
	    for (int i = 0; i < getResvDate.size(); i++) {
	        Soldout soldout = getResvDate.get(i);
	        soldout.setBiz_id(apt.getBiz_id()); // biz_id 설정
	        soldout.setR_id(apt.getR_id()); // r_id 설정
	        soldout.setSout_avail("220"); // sout_avail 설정

	        // 값 추출 확인 이것도 됨
//	        String biz_id = soldout.getBiz_id();
//	        int r_id = soldout.getR_id();
//	        String resv_date = soldout.getResv_date();
//	        String sout_avail = soldout.getSout_avail();
//	        String Resv_ID ="Resv_ID_SEQ.currval";
	        // 값들을 순서에 맞춰 새로운 리스트에 추가
//	        combinedList.add("'" + biz_id + "', " + r_id + ", '" + resv_date + "', '" + sout_avail + "', " + Resv_ID );
	        combinedList.add(soldout);
	        System.out.println("soldout: " +soldout);
	    }
	    // 새로운 리스트 출력 (옵션)
	    System.out.println("combinedList: " + combinedList);

	    
		return combinedList;
	}
	
	@GetMapping("/paymentSend")
	@ResponseBody
	public int paymentInsert( 
			HttpServletRequest req  , int resv_capa2 ,int resv_capa1) {
		
	    int paymentInsert = 0;
	    String resvDateJson = req.getParameter("resv_date");
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<String> resvDateList = null;
	    try {
	        resvDateList = objectMapper.readValue(resvDateJson, List.class);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }
	    
	    try {
	    	// Steven 추가 시작 >>
	    	// ajax에서 보낸 biz_id 값 테스트 코드
	    	System.out.println(req.getParameter("biz_id").toString());
	    	AccomPayment apt = new AccomPayment();
	    	apt.setBiz_id(req.getParameter("biz_id").toString());
	    	apt.setR_id(Integer.parseInt(req.getParameter("r_id").toString()));
	    	apt.setResv_capa(resv_capa2 +resv_capa1);
	    	//apt.setCheck_in(req.getParameter("check_in").toString());
	    	//apt.setCheck_out(req.getParameter("check_out").toString());
	    	
	    	
	    	
	    	String formattedDate1 =req.getParameter("check_in").toString();
	    	String formattedDate2 =req.getParameter("check_out").toString();
	    	//String convertedDate1 = formattedDate1.substring(2, 4) + "/" + formattedDate1.substring(4, 6) + "/" + formattedDate1.substring(6);
	    	//String convertedDate2 = formattedDate2.substring(2, 4) + "/" + formattedDate2.substring(4, 6) + "/" + formattedDate2.substring(6);
	    	System.out.println(formattedDate1);
	    	System.out.println(formattedDate2);
	    	apt.setCheck_in(formattedDate1);
	    	apt.setCheck_out(formattedDate2);
	    	apt.setPay_amt(Integer.parseInt(req.getParameter("pay_amt").toString()));
	    	
	    	
	    	
	    	apt.setResv_name(req.getParameter("resv_name").toString());
	    	apt.setResv_phone(req.getParameter("resv_phone").toString());
	    	String payDate = req.getParameter("pay_date");
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd"); // ISO 문자열을 Date 객체로 파싱하기 위한 포맷
	    	Date payDateObj = dateFormat.parse(payDate); // ISO 문자열을 Date 객체로 파싱
	    	apt.setPay_date(payDateObj); // Apartment 객체에 저장
	    	apt.setPay_method(req.getParameter("pay_method").toString());
	    	apt.setPay_status("620");
	    	apt.setUser_id(req.getParameter("user_id").toString());
	    	// 여기에 ajax에서 보낸 값들을 이용하여 apt에 필요한만큼 set 해주고 진행
	    	Soldout soldout = new Soldout();
	        paymentInsert = s06.paymentInsert(apt, resvDateList);
	        System.out.println("paymentInsert 컨트롤러 값 ==>" + paymentInsert);

	    } catch (Exception e) {
	    	System.out.println("paymentSend 트랜잭션 오류: " + paymentInsert);
	        System.out.println("paymentSend 트랜잭션 오류: " + e.getMessage());
	        System.out.println("리스트 값으로 전부 받은거 : " + resvDateList);
	        System.out.println("기존 방 인원: " + resv_capa1);
	        System.out.println("추가된 인원: " + resv_capa2);
	        e.printStackTrace();
	    }
	    System.out.println("==>"+paymentInsert);
	    return paymentInsert;
	    
	    // 이거 두개 지웡 @RequestParam("apt") AccomPayment apt, 
	    // @RequestParam("soldout") Soldout soldout
	}
	
	
	/*
	 *     System.out.println("paymentSend 트랜잭션 성공");
	    	System.out.println("paymentSend 컨트롤러의 값이 잘들 ㅓ오는지 테스트");
			System.out.println(" getBiz_name ===>"+apt.getBiz_name());
			System.out.println(" getR_name ===>"+apt.getR_name());
			System.out.println(" getBiz_id ===>"+apt.getBiz_id());
			System.out.println(" getR_id ===>"+apt.getR_id());
			System.out.println(" getUser_id ===>"+apt.getUser_id());
			System.out.println(" getPay_amt ===>"+apt.getPay_amt());
			System.out.println(" getPay_date ===>"+apt.getPay_date());
			System.out.println(" getPay_method ===>"+apt.getPay_method());
			System.out.println(" getResv_capa ===>"+apt.getResv_capa());
			System.out.println(" getResv_name ===>"+apt.getResv_name());
			System.out.println(" getResv_phone ===>"+apt.getResv_phone());
			System.out.println(" getResv_date ===>"+apt.getResv_date());
	 * @ResponseBody
	 * 
	 * @GetMapping("/paymentSend") public void paymentSend(@RequestParam String
	 * r_name, @RequestParam int biz_id, @RequestParam int r_id,
	 * 
	 * @RequestParam String user_id, @RequestParam int pay_amt, @RequestParam String
	 * pay_method,
	 * 
	 * @RequestParam int resv_capa, @RequestParam String resv_name, @RequestParam
	 * String resv_phone,
	 * 
	 * @RequestParam String resv_date) { AccomPayment apt = new AccomPayment();
	 * apt.setBiz_name(s06.getBizName(biz_id)); apt.setR_name(r_name);
	 * apt.setBiz_id(biz_id); apt.setR_id(r_id); apt.setUser_id(user_id);
	 * apt.setPay_amt(pay_amt); apt.setPay_date(new Date());
	 * apt.setPay_method(pay_method); apt.setResv_capa(resv_capa);
	 * apt.setResv_name(resv_name); apt.setResv_phone(resv_phone);
	 * apt.setResv_date(resv_date);
	 * 
	 * s06.paymentInsert(apt); }
	 */

	// 전체 조회(기본)


	@GetMapping("adminPage") 
	public String adminPage(Model model,String currentPage
							,Users users) { int userTotal =s06.userTotal(); Paging page = new
			Paging(userTotal, currentPage);
			System.out.println("컨트롤러 totalemp-> "+userTotal); // paging 작업 // 서비스이긴 하지만
			//dao작업이 필요없는 서비스는 di를 하지 않고 클래식하게 쓰는게 편함.
			System.out.println("컨트롤러 시작 끝 page-> "+ page.getStart());
			System.out.println("컨트롤러 시작 끝 page-> "+ page.getEnd()); // parameter emp ->
			//page만 추가 setting users.setStart(page.getStart()); // 시작시 1
			users.setEnd(page.getEnd()); // 시작시 10



			List<Users> userlist= s06.userlist(users);

			model.addAttribute("userTotal", userTotal);
			model.addAttribute("userlist",userlist); model.addAttribute("page", page);

			return "/views/admin/adminPage"; }

	@RequestMapping("userSeacrh1") public String userSeacrh1(Users users, Model
			model, String currentPage) {
		System.out.println("controller userSeacrh1 users->"+users); int totalUser
		=s06.conditionUserCount(users);

		Paging page =new Paging(totalUser, currentPage);

		users.setStart(page.getStart()); users.setEnd(page.getEnd());

		List<Users> listSearchUser=s06.listSearchUsers(users);

		model.addAttribute("userTotal",totalUser); model.addAttribute("page",page);
		model.addAttribute("keyword",users.getKeyword());
		model.addAttribute("userlist",listSearchUser); 
		return "/views/adminPage";

	}

}
