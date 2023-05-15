package com.example.S20230403.controller.lysController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Soldout;
import com.example.S20230403.service.lysService.accomAllController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AccomPaymentController {

	private final accomAllController s06;

	//결제쪽
	@GetMapping("/payment")
	public String r_name(Model model, AccomPayment apt, String checkIn, String checkOut) {
		//List<Payment> r_name1=s06.getr_name(pmt);
		List<AccomPayment> getApt = s06.getapt(apt);
		List<Room> r_price=s06.getR_price(apt);
		apt.setBiz_id(apt.getBiz_id());
		apt.setCheck_in(checkIn);
		apt.setCheck_out(checkOut);
	//	//system.out.println("첵인 첵아웃 ==>    "+checkIn+checkOut);


		String dateStr1 = checkIn; // 첫 번째 날짜 문자열
		String dateStr2 = checkOut; // 두 번째 날짜 문자열

		LocalDate date1 = LocalDate.parse(dateStr1); // 첫 번째 날짜 문자열을 LocalDate 객체로 변환
		LocalDate date2 = LocalDate.parse(dateStr2); // 두 번째 날짜 문자열을 LocalDate 객체로 변환

		long daysBetween = ChronoUnit.DAYS.between(date1, date2); // 두 날짜 사이의 일자 차이 계산

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E)"); // 날짜 포맷팅을 위한 DateTimeFormatter 생성
		String formattedDate1 = date1.format(formatter); // 첫 번째 날짜를 포맷팅
		String formattedDate2 = date2.format(formatter); // 두 번째 날짜를 포맷팅

		/*
		 * //system.out.println("첫 번째 날짜: " + formattedDate1); // 첫 번째 날짜 출력
		 * //system.out.println("두 번째 날짜: " + formattedDate2); // 두 번째 날짜 출력
		 * //system.out.println("일자 차이: " + daysBetween + "일"); // 일자 차이 출력
		 */


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
		////system.out.println("pmt =>" + apt);

		return "/views/payment";
	}
	
	@ResponseBody
	@GetMapping("/ResvDate")
	public List<Object> getResvDate(AccomPayment apt){
	    ////system.out.println("체크인 가지고 와줘"+ apt.getCheck_in());
	    ////system.out.println("체크아웃 가지고 와줘"+ apt.getCheck_out());
	    List<Soldout> getResvDate = s06.getResvDate(apt);

	    List<Object> combinedList = new ArrayList<>();
	    for (int i = 0; i < getResvDate.size(); i++) {
	        Soldout soldout = getResvDate.get(i);
	        soldout.setBiz_id(apt.getBiz_id()); // biz_id 설정
	        soldout.setR_id(apt.getR_id()); // r_id 설정
	        soldout.setSout_avail("220"); // sout_avail 설정

	        combinedList.add(soldout);
	        //system.out.println("soldout: " +soldout);
	    }
	    // 새로운 리스트 출력 (옵션)
	   // //system.out.println("combinedList: " + combinedList);

	    
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
	    	////system.out.println(req.getParameter("biz_id").toString());
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
			/*
			 * //system.out.println(formattedDate1); //system.out.println(formattedDate2);
			 */
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
	       // //system.out.println("paymentInsert 컨트롤러 값 ==>" + paymentInsert);

	    } catch (Exception e) {
			/*
			 * //system.out.println("paymentSend 트랜잭션 오류: " + paymentInsert);
			 * //system.out.println("paymentSend 트랜잭션 오류: " + e.getMessage());
			 * //system.out.println("리스트 값으로 전부 받은거 : " + resvDateList);
			 * //system.out.println("기존 방 인원: " + resv_capa1); //system.out.println("추가된 인원: " +
			 * resv_capa2);
			 */
	        e.printStackTrace();
	    }
	    //system.out.println("==>"+paymentInsert);
	    return paymentInsert;
	}
}
