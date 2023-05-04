package com.example.S20230403.controller.ljyController;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.S20230403.model.Room;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.service.ljyService.OwnerRoom;
import com.example.S20230403.service.ljyService.SukbakService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RoomImgController {
	
	private final SukbakService ss;
	
	// 방등록하기 위해 이미지등록폼 제공, roomInsertForm에서 이어짐
	@PostMapping(value = "/biz/roomImgInsertForm")
	public String roomImgInsertForm(OwnerRoom ownerRoom, Model model) {
		System.out.println("RoomImgController roomImgInsertForm start...");
		System.out.println("RoomImgController roomImgInsertForm ownerRoom ->"+ownerRoom);
		int nowR_id = ss.roomInsert(ownerRoom);
		
		
		Room room = new Room();
		room.setBiz_id(ownerRoom.getBiz_id());
		room.setR_id(nowR_id);
		room = ss.roomSelect(room);
		ss.updateRoomCount(ownerRoom.getBiz_id(), "add");
		System.out.println("RoomImgController roomImgInsertForm room ->"+room);
		roomImgDelete(room);
		
		model.addAttribute("room", room);
		return "/views/biz/roomImgInsertForm";
	}
		
	// 이미지파일 저장
	@PostMapping(value = "/biz/roomImgInsert")
	public String roomImgInsert(MultipartFile[] files, Model model, Room room) throws Exception {
		System.out.println("RoomImgController roomImgInsert 시작...");
//		String uploadPath = request.getSession().getServletContext().getRealPath("/static/img/room/");
		String uploadPath = "src/main/resources/static/img/room/";
		System.out.println("RoomImgController roomImgInsert uploadPath -> "+uploadPath);
		System.out.println("RoomImgController roomImgInsert room -> "+room);
			
		for (MultipartFile file : files) {
				// 파일 확장자
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				// 서버에 저장할 파일명
				String savedName = uploadFile(file.getBytes(), uploadPath, room, extension);
				System.out.println("RoomImgController roomImgInsertForm savedName -> "+savedName);
				
		}
		
		room = ss.roomSelect(room);
		
		Room_Img ri = new Room_Img();
		ri.setBiz_id(room.getBiz_id());
		ri.setR_id(room.getR_id());
		
		List<Room> riList = ss.selectRoomImgList(ri);
		System.out.println("RoomImgController roomSelectForm riList -> "+riList);
		model.addAttribute("riList", riList);
		model.addAttribute("room", room);
		return "/views/biz/roomSelectForm";
		
	}
	
	// sql문 2개 사용중
	private String uploadFile(byte[] fileData, String uploadPath, Room room, String extension) throws Exception {
		System.out.println("RoomImgController uploadFile 메소드 실행");
		int imgNum = ss.getImgNum(room);
		imgNum +=1;
		System.out.println("RoomImgController uploadFile imgNum -> "+imgNum);
		String savedName = room.getBiz_id()+room.getR_id()+imgNum+"."+extension;
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);
		
		System.out.println("uploadPath -> "+uploadPath);
		System.out.println("RoomImgController uploadFile 메소드 imgNum -> "+imgNum);
		System.out.println("RoomImgController uploadFile 메소드 savedName -> "+savedName);
		
		room.setR_img_id(imgNum);
		room.setR_img(savedName);
		System.out.println("RoomImgController uploadFile 메소드 room -> "+room);
		ss.roomImgInsert(room);
		return savedName;
	}
	
	/*
	 * @PostMapping(value = "/biz/roomSelectForm") public String
	 * roomSelectForm(@AuthenticationPrincipal PrincipalDetail userDetail, Room
	 * room, Model model) {
	 * System.out.println("RoomImgController roomSelectForm room -> "+room); //
	 * 이미지리스트 가져와야함 Room_Img ri = new Room_Img(); ri.setBiz_id(room.getBiz_id());
	 * ri.setR_id(room.getR_id());
	 * System.out.println("RoomImgController roomSelectForm ri -> "+ri);
	 * List<Room_Img> riList = ss.selectRoomImgList(ri);
	 * model.addAttribute("riList", riList); model.addAttribute("room", room);
	 * return "/views/biz/roomSelectForm"; }
	 */
	
	@PostMapping(value = "/biz/roomUpdateForm")
	public String roomUpdateForm(Room room, Model model) {
		System.out.println("RoomImgController roomUpdateForm 시작...");
		System.out.println("RoomImgController roomUpdateForm room -> "+room);

		Room_Img ri = new Room_Img();
		ri.setBiz_id(room.getBiz_id());
		ri.setR_id(room.getR_id());
		
		List<Room> riList = ss.selectRoomImgList(ri);
		System.out.println("RoomImgController roomUpdateForm riList -> "+riList);
		
		model.addAttribute("room", room);
		model.addAttribute("riList", riList);
		return "/views/biz/roomUpdateForm";
	}
	
	// room정보를 업데이트하고, 이미지재등록으로 진입 시, 이미지 삭제+이미지 재등록...
	@PostMapping(value = "/biz/roomUpdate")
	public String roomUpdate(@ModelAttribute(value = "roomUpdateChk") String roomUpdateChk, OwnerRoom ownerRoom, Model model) {
		System.out.println("RoomImgController roomUpdate 시작...");
		System.out.println("RoomImgController roomUpdate ownerRoom -> "+ownerRoom);
		System.out.println("RoomImgController roomUpdate roomUpdateChk -> "+roomUpdateChk);
		ss.roomUpdate(ownerRoom);
		// ownerRoom으로 가져왔으므로 룸 필요..
		Room room = new Room();
		room.setBiz_id(ownerRoom.getBiz_id());
		room.setR_id(ownerRoom.getR_id());
		room = ss.roomSelect(room);
		model.addAttribute("room", room);
		
		if (roomUpdateChk.equals("이미지재등록")) {
			System.out.println("RoomImgController roomUpdate 이미지재등록, 기존이미지 모두 삭제");
			//기존 서버에 저장된 이미지와 DB의 데이터 삭제해야함
			roomImgDelete(room);
			return "/views/biz/roomImgInsertForm";
		}
		
		return "/views/biz/roomSelectForm";
	}
	
	// 이미지 삭제로직, DB와 서버에 저장된 이미지 삭제하는 "메소드"
	// biz_id, r_id로 해당 이미지 로우들 모두 찾아서 삭제(delete)
	private void roomImgDelete(Room room) {
		System.out.println("RoomImgController roomImgDelete room -> "+room);
		String imgPath = "src/main/resources/static/img/room/";
		String deleteFileName = "";
		
		Room_Img ri = new Room_Img();
		ri.setBiz_id(room.getBiz_id());
		ri.setR_id(room.getR_id());
		System.out.println("RoomImgController roomImgDelete  ri ->"+ri);
		
		List<Room> riList = ss.selectRoomImgList(ri);
		System.out.println("RoomImgController roomImgDelete riList -> "+riList);
		
		for (Room room_img : riList) {
			String r_img = room_img.getR_img();
			deleteFileName = imgPath+r_img;
			File file = new File(deleteFileName);
			file.delete();
		}
		ss.roomImgDelete(room);
	}

}
