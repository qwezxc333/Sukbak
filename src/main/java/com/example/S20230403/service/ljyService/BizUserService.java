package com.example.S20230403.service.ljyService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.S20230403.dao.ljyDao.SukbakDao;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Comm;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BizUserService implements SukbakService{
	
	private final SukbakDao sd01; 
	
	/*
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> HandlerExceptionResolver(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error message: " + e.getMessage());
	}
	*/
	
	// 하영이가해줌
	@Override
	public void accomInsert(OwnerUser ownerUser) {
		Accom accom = ownerUser.toAccom();
		//log.info("비즈서비스01 accomInsert 시작...");
		sd01.accomInsert(accom);
	}

	
	@Override
	public List<Accom> accomList(String user_id) {
		//log.info("비즈서비스01 accomList 시작...");
		List<Accom> accomList =  sd01.accomList(user_id);
		System.out.println("accomList -> "+accomList.toString());
		return accomList;
	}

	@Override
	public List<Comm> selectCommList(int code) {
		//log.info("비즈서비스01 selectCommList 시작...");
		List<Comm> stats = sd01.selectCommList(code); 
		return stats;
	}
	
	// 사업자 로우 하나 불러오기용
	@Override
	public Accom accomSelect(String biz_id) {
		//log.info("비즈서비스01 accomSelect 시작...");
		Accom accom = sd01.accomSelect(biz_id);
		return accom;
	}
	
	// 업데이트문에 사용
	@Override
	public void accomUpdate(Accom accom) {
		//log.info("비즈서비스01 accomUpdate 시작...");
		sd01.accomUpdate(accom);
	}

	// 계정정보변경을 위해 비밀번호 검증, 아이디에 매칭되는 실제 비밀번호를 리턴할것
	@Override
	public String getDBPassword(String user_id) {
		//log.info("비즈서비스01 getDBPassword 시작...");
		return sd01.getDBPassword(user_id);
	}
	
	// 아이디로 현재로그인한 유저의 전체로우 가져옴
	@Override
	public Users getNowLoginUser(String user_id) {
		//log.info("비즈서비스01 getNowLoginUser 시작...");
		return sd01.getNowLoginUser(user_id);
	}


	// 실제로 삭제 X
	@Override
	public int userDelete(String user_id) {
		//log.info("비즈서비스01 userDelete 시작...");
		return sd01.userDelete(user_id);
	}


	@Override
	public void userUpdate(Users user) {
		//log.info("비즈서비스01 userUpdate 시작...");
		sd01.userUpdate(user);
	}


	@Override
	public List<Room> roomList(String user_id) {
		//log.info("비즈서비스01 roomList 시작...");
		List<Room> roomList = sd01.roomList(user_id);
		return roomList;
	}


	@Override
	public int roomInsert(OwnerRoom ownerRoom) {
		//log.info("비즈서비스01 roomInsert 시작...");
		Room room = ownerRoom.toRoom();
		//방번호 추출하기
		Integer maxR_id = sd01.roomIdExtract(room);
		System.out.println("비즈서비스01 roomInsert maxR_id -> "+maxR_id);
		maxR_id +=1;
		System.out.println("비즈서비스01 roomInsert maxR_id += 1 -> "+maxR_id);
		room.setR_id(maxR_id);
		sd01.roomInsert(room);
		return maxR_id;
	}


	@Override
	public int getImgNum(Room room) {
		//log.info("비즈서비스01 getImgNum 시작...");
		int imgNum = sd01.getImgNum(room);
		System.out.println("비즈서비스01 getImgNum imgNum -> "+imgNum);
		return imgNum;
	}


	@Override
	public void roomImgInsert(Room room) {
		//log.info("비즈서비스01 roomImgInsert 시작...");
		sd01.roomImgInsert(room);
		
	}


	@Override
	public List<Room> selectRoomImgList(Room_Img ri) {
		//log.info("비즈서비스01 selectRoomImgList 시작...");
		return sd01.selectRoomImgList(ri);
	}


	@Override
	public Room roomSelect(Room room) {
		//log.info("비즈서비스01 roomSelect 시작...");
		return sd01.roomSelect(room);
	}


	@Override
	public void roomImgDelete(Room room) {
		//log.info("비즈서비스01 roomImgDelete 시작...");
		sd01.roomImgDelete(room);
		
	}


	@Override
	public void roomUpdate(OwnerRoom ownerRoom) {
		Room room = ownerRoom.toRoom();
		//log.info("비즈서비스01 roomUpdate 시작...");
		sd01.roomUpdate(room);
		
	}


	@Override
	public void roomStatus(Room room, String string) {
		//log.info("비즈서비스01 roomUpdate 시작...");
		sd01.roomStatus(room, string);
		
	}


	@Override
	public boolean getBizId(String biz_id) {
		//log.info("비즈서비스01 getBizId 시작...");
		return sd01.getBizId(biz_id);
	}


	@Override
	public void updateRoomCount(String biz_id, String string) {
		//log.info("비즈서비스01 updateRoomCount 시작...");
		sd01.updateRoomCount(biz_id, string);
	}


	@Override
	public void accomStatus(Accom accom, String string) {
		//log.info("비즈서비스01 accomStatus 시작...");
		sd01.accomStatus(accom, string);
		
	}


	@Override
	public List<Room> roomListSelectWithAccom(String biz_id) {
		//log.info("비즈서비스01 roomListSelectWithAccom 시작...");
		return sd01.roomListSelectWithAccom(biz_id);
	}


	@Override
	public List<Room_Img> selectAccomAllRoomImgList(String biz_id) {
		//log.info("비즈서비스01 selectAccomAllRoomImgList 시작...");
		return sd01.selectAccomAllRoomImgList(biz_id);
	}

 


}
