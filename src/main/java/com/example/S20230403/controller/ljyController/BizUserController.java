package com.example.S20230403.controller.ljyController;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Users;
import com.example.S20230403.service.ljyService.OwnerUser;
import com.example.S20230403.service.ljyService.SukbakService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
public class BizUserController {
	
	private final SukbakService ss01;
	//에러 페이지로 던지기
	@ExceptionHandler
	public String exceptionThrows(Exception e, Model model) {
		String exception = e.toString();
		model.addAttribute("exception", exception);
		return "/views/biz/exceptionThrows";
	}
	
	// 사업자메인
	@GetMapping(value = "/biz/bizMain") 
	public String bizMain(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		//log.info("비즈컨트롤러01 goBizMain start...");
		Accom accom = new Accom();
		String user_id = userDetail.getUsername();
		accom.setUser_id(user_id);
		model.addAttribute("accom", accom);
		model.addAttribute("user_id", user_id);
		//System.out.println("user_id -> "+user_id);
		//System.out.println("accom ->"+accom);
		// 앞이 뷰에서받은 파라미터 비번, 뒤가 db비번
		//if(passwrodEncoder.matches(user_id, eerq))
		
		List<Accom> accomList = ss01.accomList(user_id);
		for (Accom accoms :accomList ) {
			//System.out.println("비즈컨트롤01 bizMain accomList accom->"+accoms);
		}
		model.addAttribute("accomList", accomList) ;
		
		return "/views/biz/bizMain";
	}
	
	// 유저 id로 업체정보 불러오기
		@ResponseBody
		@GetMapping(value = "/biz/accomMng")
		public List<Accom> accomList(@AuthenticationPrincipal PrincipalDetail userDetail) {
			//System.out.println("비즈컨트롤01 accomMng 시작...");
			String user_id = userDetail.getUsername();
			//System.out.println("비즈컨트롤01 user_id -> "+user_id);
			List<Accom> accomList = ss01.accomList(user_id);
			for (Accom accom :accomList ) {
				//System.out.println("비즈컨트롤01 accomMng accom->"+accom);
			}
			return accomList;
		}
		
	// bizMain페이지 버튼과의 상호작용에 따라 각 버튼에 분기정보와 함께 분기 찾아감
	@PostMapping(value = "/biz/accomMngChk")
	public String accomMng(Accom accom, 
						   @RequestParam(value = "accomMngChk") String accomMngChk,
						   Model model) {
		//System.out.println("비즈컨트롤러01 accomMng start...");
		//System.out.println("accomMngChk -> "+accomMngChk);
		//System.out.println("accom ->"+accom.toString());
		
		String biz_id = accom.getBiz_id();
		//System.out.println("biz_id -> "+biz_id);
		accom = ss01.accomSelect(biz_id);
		String user_id = accom.getUser_id();
		model.addAttribute("biz_id", biz_id);
		List<Accom> accomList = ss01.accomList(user_id);
		List<Room_Img> accomRoomImgList = ss01.selectAccomAllRoomImgList(biz_id);
		switch (accomMngChk) {
		case "상세확인":
			//System.out.println("비즈컨트롤러01 accomMng 상세확인 case");
			// 아이디로 로우 하나 가져옴
			ss01.updateRoomCount(biz_id, "update");
			////System.out.println("accom -> "+accom.toString());
			model.addAttribute("accom", accom);
			model.addAttribute("accomRoomImgList", accomRoomImgList);
			return "/views/biz/accomSelectForm";
		case "수정":
			////System.out.println("비즈컨트롤러01 accomMng 수정 case");
			//바로 수정은 안하지만 로우를 가져와야함 따라서 위 로직 같이 사용
			model.addAttribute("accom", accom);
			return "/views/biz/accomUpdateForm";
		case "숨기기":
			////System.out.println("비즈컨트롤러01 accomMng 숨기기 case");
			ss01.accomStatus(accom, "hidden");
			accomList = ss01.accomList(biz_id);
			model.addAttribute("accomList", accomList);
			return "redirect:/biz/bizMain";
		case "숨김해제":
			////System.out.println("비즈컨트롤러01 accomMng 숨김해제 case");
			ss01.accomStatus(accom, "open");
			accomList = ss01.accomList(biz_id);
			model.addAttribute("accomList", accomList);
			return "redirect:/biz/bizMain";
		case "삭제":
			////System.out.println("비즈컨트롤러01 accomMng 삭제 case");
			ss01.accomStatus(accom, "delete");
			accomList = ss01.accomList(biz_id);
			model.addAttribute("accomList", accomList);
			return "redirect:/biz/bizMain";
		case "객실조회":
			////System.out.println("비즈컨트롤01 accomMng 객실조회 case");
			accom = ss01.accomSelect(biz_id);
			List<Room> roomList = ss01.roomListSelectWithAccom(biz_id);
			// 각 room에 가격 포매팅해줌..
			for (Room room :roomList ) {
				////System.out.println("비즈컨트롤01 rooms accom->"+room);
				room.setR_formatPrice(room.r_priceFormating(room.getR_price())); 
			}
			model.addAttribute("roomList", roomList);
			model.addAttribute("accom", accom);
			return "/views/biz/rooms";
		default:
			return "/views/biz/exceptionThrows";
		}
	}
	
	// 업체 수정값 입력 페이지 보냄
	@PostMapping(value = "/biz/accomUpdateForm")
	public String accomUpdateForm(Accom accom, Model model) {
		////System.out.println("비즈컨트롤러 accomUpdateForm 시작...");
		////System.out.println("accom -> "+accom.toString());
		String biz_id = accom.getBiz_id();
		accom = ss01.accomSelect(biz_id);
		model.addAttribute("accom", accom);
		return "/views/biz/accomUpdateForm";
	}
	
	// 업체 수정 sql 실행 후 사업자메인으로 보냄
	@PostMapping(value = "/biz/accomUpdate")
	public String accomUpdate(OwnerUser ownerUser) {
		////System.out.println("비즈컨트롤러 accomUpdate 시작...");
		////System.out.println("비즈컨트롤러 accomUpdate ownerUser -> "+ownerUser);
		/*
		 * if (accom.getPool() == null) accom.setPool("N"); if (accom.getParking() ==
		 * null) accom.setParking("N"); if (accom.getCafe() == null) accom.setCafe("N");
		 * if (accom.getRestaurant() == null) accom.setRestaurant("N"); if
		 * (accom.getStore() == null) accom.setStore("N"); if (accom.getSauna() == null)
		 * accom.setSauna("N"); if (accom.getLaundry() == null) accom.setLaundry("N");
		 * if (accom.getFitness() == null) accom.setFitness("N");
		 */
		// 
		Accom accom = ownerUser.toAccom();
		////System.out.println("accom -> "+accom.toString());
		ss01.accomUpdate(accom);
		return "redirect:/biz/bizMain";
	}
	
	// 업체정보 입력 페이지 유저아이디가지고 진입
	@PostMapping(value = "/biz/accomInsertForm")
	public String accomInsertForm (@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		////System.out.println("비즈컨트롤러01 accomInsertForm 시작...");
		String user_id = userDetail.getUsername();
		////System.out.println("비즈컨트롤러01 accomInsertForm user_id -> "+user_id);
		model.addAttribute("user_id", user_id);
		return "/views/biz/accomInsertForm";	
	}
	
	
	// 업체 등록 sql실행 후 사업자메인으로 보냄
	// 업체정보 등록받아서  DB에 넣는 로직
	@PostMapping(value = "/biz/accomInsert")
	public String accomInsert(OwnerUser ownerUser, Model model) {
		////System.out.println("비즈컨트롤러01 accomInsert start...");
		////System.out.println(ownerUser.toString());
		ss01.accomInsert(ownerUser);
		return "redirect:/biz/bizMain";
	}
	
	// accomInsertForm , accomUpdateForm ->
	// 주소 찾기위한 새로운 브라우저 호출
	@GetMapping (value = "/biz/accomAddrGetForm")
	public String accomAddrGerForm() {
		////System.out.println("비즈컨트롤러01 accomAddrGetForm 시작...");
		return "/views/biz/accomAddrGetForm";
	}
	
	
	/*
	 *  사업자정보 관련로직, 안씀
	*/
	@GetMapping(value = "/biz/checkBizId") 
	@ResponseBody
	public String checkBizId (@RequestParam String biz_id) {
		////System.out.println("비즈컨트롤러01 checkBizId 시작...");
		////System.out.println("비즈컨트롤러01 checkBizId biz_id -> "+biz_id);
		String biz_IdRegex = "\\d{3}-\\d{3}-\\d{4}";
		if (!biz_id.matches(biz_IdRegex)) {
			// 형식에 맞지않음 리턴
			return  "nonMatched";
		} else if (! ss01.getBizId(biz_id)) return "duplicate";
		else return "available";
	}
	
	// 사용자가 개인정보수정등에 접근하기 전에 비밀번호 인증 폼으로 보내는로직, 유저가 뭘보냈는지 중요
	@GetMapping(value = "/biz/usersInfoChkForm")
	public String usersInfoChkForm (String userAction, 
								Model model) {
		////System.out.println("비즈컨트롤01 usersInfoChkForm 시작...");
		////System.out.println("userAction -> "+userAction);
		model.addAttribute("userAction",userAction);
		
		return "/views/biz/usersInfoChkForm";
	}
	
	//위 로직을 처리, 유저아이디, 유저정보 체크 후 리턴
	@PostMapping(value = "/biz/usersInfoChk")
	private String usersInfoChk (@AuthenticationPrincipal PrincipalDetail userDetail,
								 @ModelAttribute(value = "password")String insertPassword, 
								 @ModelAttribute(value = "userAction")String userAction,
								 Model model) {
		////System.out.println("userAction -> "+userAction);
		////System.out.println("insertPassword -> "+insertPassword);
		String user_id = userDetail.getUsername();
		String DBPassword = ss01.getDBPassword(user_id);
		// 해시코드 암호화로 비교해야함
		////System.out.println("insertPassword -> "+insertPassword);
		////System.out.println("DBPassword -> "+DBPassword);
		
		if (!insertPassword.equals(DBPassword)) {
			////System.out.println("usersInfoChk 로그인 실패");
			model.addAttribute("msg", "비밀번호 인증에 실패했습니다");
			return "redirect:/biz/bizMain";
		} else {
			// 유저아이디로 현재로그인한 유저의 로우를 모두 가져옴
			////System.out.println("usersInfoChk 로그인 성공");
			Users user = ss01.getNowLoginUser(user_id);
			////System.out.println("nowLoginUser - > "+user.toString());
			model.addAttribute("nowLoginUser", user);
				switch (userAction) {
					case "계정정보 변경":
						////System.out.println("비즈컨트롤 usersInfoChk 계정정보 변경");
						return "/views/biz/usersUpdateForm";
					// 메일인증 시스템, 이메일인증 폼
					case "계정 양도":
						////System.out.println("비즈컨트롤 usersInfoChk 계정 양도");
						return "/views/biz/usersTradeForm";
					case "회원 탈퇴":
						////System.out.println("비즈컨트롤 usersInfoChk 계정 삭제");
						return "/views/biz/usersDeleteForm";
					default:
						return "redirect:/biz/bizMain";
				}
		}
	}
	
	// usersInfoChk에서 이어짐 /views/biz/usersUpdateForm에서 입력받은 폼을 DB에 update
	@PostMapping(value = "/biz/usersUpdate") 
	public String usersUpdate(Users user) {
		////System.out.println("비즈컨트롤01 usersUpdate 시작...");
		////System.out.println("nowLoginUser -> "+user.toString());
		ss01.userUpdate(user);
		return "redirect:/biz/bizMain";
	}
	
	// usersInfoChk에서 이어짐, /views/biz/usersDeleteForm 유저삭제지만 실제로는 삭제X 유저상태만 바꿈
	@PostMapping(value = "/biz/userDelete")
	public String userDelete(Users user) {
		////System.out.println("비즈컨트롤01 userDelete 시작...");
		String user_id = user.getUser_id();
		////System.out.println("비즈컨트롤01 userDelete user_id -> "+user_id);
		int deleteResult = ss01.userDelete(user_id);
		////System.out.println("비즈컨트롤01 userDelete deleteResult -> "+deleteResult);
		return "redirect:/main";
	}
		
	@GetMapping(value = "/biz/rooms")
	public String rooms(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		String user_id = userDetail.getUsername();
		////System.out.println("비즈컨트롤01 rooms 시작...");
		List<Room> roomList = ss01.roomList(user_id);
		DecimalFormat decimalFormat = new DecimalFormat("###,### 원");
		for (Room room :roomList ) {
			////System.out.println("비즈컨트롤01 rooms accom->"+room);
			room.setR_formatPrice(decimalFormat.format(room.getR_price()));
		}
		model.addAttribute("roomList", roomList);
		return "/views/biz/rooms";
	}
	
	// 유저아이디로 사업자등록번호 뽑아와서 등록된 모든 업체의 모든 룸을 가져와야함
	@ResponseBody
	@GetMapping(value = "/biz/roomMng")
	public List<Room> roomMng(@AuthenticationPrincipal PrincipalDetail userDetail) {
		String user_id = userDetail.getUsername();
		////System.out.println("비즈컨트롤01 roomMng 시작...");
		List<Room> roomList = ss01.roomList(user_id);
		for (Room room :roomList ) {
			////System.out.println("비즈컨트롤01 accomMng accom->"+room);
		}
		return roomList;
	}
	
	// rooms페이지 버튼과의 상호작용에 따라 각 버튼에 분기정보와 함께 분기 찾아감
	@PostMapping(value = "/biz/roomMngChk")
	public String roomMngChk(Room room, 
						   @RequestParam(value = "roomMngChk") String roomMngChk,
						   Model model) {
		////System.out.println("비즈컨트롤러01 accomMng start...");
		////System.out.println("roomMngChk -> "+roomMngChk);
		////System.out.println("accom ->"+room.toString());
		
		// 방 하나 딱 찝을거라 복합키구성하는 2개키 필요...
		String biz_id = room.getBiz_id();
		int r_id = room.getR_id();
		////System.out.println("biz_id -> "+biz_id);
		////System.out.println("room_id -> "+r_id);
		room = ss01.roomSelect(room);
		// room.r_priceFormating는 빈필드라 메소드 호출해서 채워줌
		room.setR_formatPrice(room.r_priceFormating(room.getR_price()));
		// 방 이미지를 출력해줘야 되서 이곳에서 이미지 호출, 이미지호출 필요한 분기에만 모델에 추가해줌
		Room_Img ri = new Room_Img();
		ri.setBiz_id(room.getBiz_id());
		ri.setR_id(room.getR_id());
		////System.out.println("RoomImgController roomSelectForm ri -> "+ri);
		List<Room> riList = ss01.selectRoomImgList(ri);
		
		model.addAttribute("biz_id", biz_id);
		model.addAttribute("r_id", r_id);
		model.addAttribute("riList", riList);
		switch (roomMngChk) {
		case "상세확인":
			// 아이디로 로우 하나 가져옴, 방상세정보 확인
			////System.out.println("비즈컨트롤러01 roomMngChk 상세확인 case");
			////System.out.println("room -> "+room.toString());
			////System.out.println("riList -> "+riList);
			model.addAttribute("room", room);
			return "/views/biz/roomSelectForm";
		case "수정":
			////System.out.println("비즈컨트롤러01 roomMngChk 수정 case");
			//바로 수정은 안하지만 로우를 가져와야함 따라서 위 다오 로직 같이 사용		
			model.addAttribute("room", room);
			return "/views/biz/roomUpdateForm";
		case "숨기기":
			////System.out.println("비즈컨트롤러01 roomMngChk 숨기기 case");
			ss01.roomStatus(room, "hidden");
			riList = ss01.selectRoomImgList(ri);
			return "redirect:/biz/rooms";
		case "숨김해제":
			////System.out.println("비즈컨트롤러01 roomMngChk 숨김해제 case");
			ss01.roomStatus(room, "open");
			riList = ss01.selectRoomImgList(ri);
			return "redirect:/biz/rooms";
		case "삭제":
			////System.out.println("비즈컨트롤러01 roomMngChk 삭제 case");
			model.addAttribute("room", room);
			ss01.roomStatus(room, "delete");
			ss01.updateRoomCount(biz_id, "minus");
			return "redirect:/biz/rooms";
		default:
			return "/views/biz/exceptionThrows";
		}
	}
	
	// 객실 새로 삽입, 유저아이디를 가지고 업체명+사업자번호로 구성된 Accom리스트 리턴 
	@PostMapping(value = "biz/roomInsertForm")
	public String roomInsertForm(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
		String user_id = userDetail.getUsername();
		////System.out.println("비즈컨트롤러01 roomInsertForm start...");
		////System.out.println("user_id -> "+user_id);
		List<Accom> accomList = ss01.accomList(user_id);
		model.addAttribute("accomList", accomList);
		return "/views/biz/roomInsertForm";
	}
	
	//
	@GetMapping(value = "/biz/roomListSelectWithAccom") 
	public String roomListSelectWithAccom(String biz_id, Model model) {
		////System.out.println("비즈컨트롤01 roomListSelectWithAccom biz_id -> "+biz_id);
		Accom accom = ss01.accomSelect(biz_id);
		List<Room> roomList = ss01.roomListSelectWithAccom(biz_id);
		model.addAttribute("roomList", roomList);
		model.addAttribute("accom", accom);
		return "/views/biz/rooms";
	}
	
	
	
	
}
