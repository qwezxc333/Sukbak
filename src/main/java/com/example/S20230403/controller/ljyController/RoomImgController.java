package com.example.S20230403.controller.ljyController;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	// 이미지 업로드 위해 절대경로 및 상대경로 추출용, application.yml에 선언되어있음
	@Value("${file-room.dir}")
	private String uploadPath;
	
	// 방등록하기 위해 이미지등록폼 제공, roomInsertForm에서 이어짐
	@PostMapping(value = "/biz/roomImgInsertForm")
	public String roomImgInsertForm(OwnerRoom ownerRoom, Model model) {
		//System.out.println("RoomImgController roomImgInsertForm start...");
		//System.out.println("RoomImgController roomImgInsertForm ownerRoom ->"+ownerRoom);
		// 일단 방 저장, ownerRoom에는 부대시설이 체크되어있지 않으면 N으로 넣어주는 로직이 포함되고, room형식의 필드임
		int nowR_id = ss.roomInsert(ownerRoom);
		
		Room room = new Room();
		room.setBiz_id(ownerRoom.getBiz_id());
		room.setR_id(nowR_id);
		room = ss.roomSelect(room);
		// 방이 추가되는 로직이기 때문에  biz_id로 Room테이블총 객실수 구해서 Accom테이블에 업데이트
		ss.updateRoomCount(ownerRoom.getBiz_id(), "update");
		//System.out.println("RoomImgController roomImgInsertForm room ->"+room);
		roomImgDelete(room);
		
		model.addAttribute("room", room);
		return "/views/biz/roomImgInsertForm";
	}
		
	// 이미지파일 저장
	@PostMapping(value = "/biz/roomImgInsert")
	public String roomImgInsert(MultipartFile[] files, Model model, Room room,
								HttpServletRequest request) throws Exception {
		//System.out.println("RoomImgController roomImgInsert 시작...");
		//System.out.println("RoomImgController roomImgInsert files.length -> "+files.length);
		
		File convertFile = null;
		String rootPath = uploadPath.substring(0, uploadPath.lastIndexOf("\\img\\room\\"));
		
		//파일을 하나씩 풀어서 저장할 준비해줌...
		for (MultipartFile file : files) {
				// 파일 확장자 추출
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				// 서버에 저장할 파일명, 파일확장자가 공백이면 파일이 없으므로 if문을 타지 않음 -> 파일 저장X, db저장  X
				if (!extension.equals("")) {
				// 저장할 이름을 추출하는 메소드 uploadFile
				String savedName = uploadFile(file.getBytes(), room, extension);
				// 실제 파일 저장, 컨택스트패스+추출한 파일명으로 
				convertFile = new File(rootPath + savedName);
				//System.out.println("RoomImgController roomImgInsertForm savedName -> "+savedName);
				//System.out.println("RoomImgController roomImgInsert convertFile -> "+convertFile);

				file.transferTo(convertFile);
				
				}
		}
		
		// 이미지 저장을 끝내고 사용자에게 보여줄 모델들과 함께 객실확인페이지로
		// 객실확인페이지에는 객실이미지 출력, 해당 객실 의 정보 모두 가져가야함 room room_img
		room = ss.roomSelect(room);
		room.r_priceFormating(room.getR_price());
		Room_Img ri = new Room_Img();
		ri.setBiz_id(room.getBiz_id());
		ri.setR_id(room.getR_id());
		List<Room> riList = ss.selectRoomImgList(ri);
		//System.out.println("RoomImgController roomSelectForm riList -> "+riList);
		
		model.addAttribute("riList", riList);
		model.addAttribute("room", room);
		model.addAttribute("uploadPath", uploadPath);
		return "/views/biz/roomSelectForm";
		
	}
	
	// sql문 2개 사용중, 이미지 이름 추출하기 위한 메소드
	private String uploadFile(byte[] fileData, Room room, String extension) throws Exception {
		//System.out.println("RoomImgController uploadFile 메소드 실행");
		
		String extractPath = uploadPath.substring(uploadPath.lastIndexOf("\\img\\room\\"));
		
		// 폴더 없으면 폴더 생성
		File fileDirectory = new File(uploadPath);
		if (!fileDirectory.exists()) {
			// 신규 폴더(Directory) 생성 
			fileDirectory.mkdirs();
			//System.out.println("8.업로드용 폴더 생성 : " + uploadPath);
		}
		
		// room_img의 복합키중 하나인 r_id 는 이미지가 하나씩 추가될 때마가 1씩 증가해야됨
		int imgNum = ss.getImgNum(room);
		imgNum +=1;
		//System.out.println("RoomImgController uploadFile imgNum -> "+imgNum);
		// 저장명은 = 컨택스트 경로 + 해당객실 사업자등록번호 + 방번호 + 이미지번호 + 확장자명
		// 따라서 뷰에서 이미지를 호출할 때에는 r_img 컬럼만을 사용해서 호출이 가능해진다....
		String savedName = extractPath+ room.getBiz_id()+room.getR_id()+imgNum+"."+extension;
		//System.out.println("RoomImgController uploadFile savedName -> "+savedName);
		
		// 로직을 바꿔서 더이상 사용하지 않음
		/*
		 * File target = new File(imgPath, savedName); FileCopyUtils.copy(fileData,
		 * target);
		 */
		//convertFile = new File(rootPath+savedName);
		
		// DB에 저장하는 로직, DB저장 후 savedName을 리턴시켜 그것으로 파일을 저장한다
		room.setR_img_id(imgNum);
		room.setR_img(savedName);
		//System.out.println("RoomImgController uploadFile 메소드 room -> "+room);
		ss.roomImgInsert(room);
		return savedName;
	}
	
	//
	@PostMapping(value = "/biz/roomUpdateForm")
	public String roomUpdateForm(Room room, Model model) {
		//System.out.println("RoomImgController roomUpdateForm 시작...");
		//System.out.println("RoomImgController roomUpdateForm room -> "+room);

		Room_Img ri = new Room_Img();
		ri.setBiz_id(room.getBiz_id());
		ri.setR_id(room.getR_id());
		
		List<Room> riList = ss.selectRoomImgList(ri);
		//System.out.println("RoomImgController roomUpdateForm riList -> "+riList);
		
		model.addAttribute("room", room);
		model.addAttribute("riList", riList);
		return "/views/biz/roomUpdateForm";
	}
	
	// room정보를 업데이트하고, 이미지재등록으로 진입 시, 이미지 삭제+이미지 재등록...
	@PostMapping(value = "/biz/roomUpdate")
	public String roomUpdate(@ModelAttribute(value = "roomUpdateChk") String roomUpdateChk, OwnerRoom ownerRoom, Model model) {
		//System.out.println("RoomImgController roomUpdate 시작...");
		//System.out.println("RoomImgController roomUpdate ownerRoom -> "+ownerRoom);
		//System.out.println("RoomImgController roomUpdate roomUpdateChk -> "+roomUpdateChk);
		ss.roomUpdate(ownerRoom);
		// ownerRoom으로 가져왔으므로 룸 필요..
		Room room = new Room();
		room.setBiz_id(ownerRoom.getBiz_id());
		room.setR_id(ownerRoom.getR_id());
		room = ss.roomSelect(room);
		model.addAttribute("room", room);
		
		if (roomUpdateChk.equals("이미지재등록")) {
			// 서버에 저장된 기존 이미지들과 DB의 데이터 삭제하고 등록한다
			//System.out.println("RoomImgController roomUpdate 이미지재등록, 기존이미지 모두 삭제");
			roomImgDelete(room);
			return "/views/biz/roomImgInsertForm";
		}
		return "/views/biz/roomSelectForm";
	}
	
	// 이미지 삭제로직, DB와 서버에 저장된 이미지 삭제하는 "메소드"
	// biz_id, r_id로 해당 이미지 로우들 모두 찾아서 삭제(delete)
	private void roomImgDelete(Room room) {
		//System.out.println("RoomImgController roomImgDelete room -> "+room);
		
		String deleteFileName = "";
		String rootPath = uploadPath.substring(0, uploadPath.lastIndexOf("\\img\\room\\"));
		
		// 삭제할 이미지 리스트 뽑아오기
		Room_Img ri = new Room_Img();
		ri.setBiz_id(room.getBiz_id());
		ri.setR_id(room.getR_id());
		//System.out.println("RoomImgController roomImgDelete  ri ->"+ri);
		
		List<Room> riList = ss.selectRoomImgList(ri);
		//System.out.println("RoomImgController roomImgDelete riList -> "+riList);
	
		for (Room room_img : riList) {
			deleteFileName = rootPath + room_img.getR_img();
			//System.out.println("RoomImgController roomImgDelete deleteFileName -> "+deleteFileName);
			File file = new File(deleteFileName);
			file.delete();
		}
		ss.roomImgDelete(room);
	}

}
