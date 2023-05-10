package com.example.S20230403.service.ljyService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.S20230403.dao.ljyDao.SukbakDao;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Comm;
import com.example.S20230403.model.Room;
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
		log.info("비즈서비스01 accomInsert 시작...");
		sd01.accomInsert(accom);
	}

	
	@Override
	public List<Accom> accomList(String user_id) {
		log.info("비즈서비스01 accomList 시작...");
		List<Accom> accomList =  sd01.accomList(user_id);
		System.out.println("accomList -> "+accomList.toString());
		return accomList;
	}

	@Override
	public List<Comm> selectCommList(int code) {
		log.info("비즈서비스01 selectCommList 시작...");
		List<Comm> stats = sd01.selectCommList(code); 
		return stats;
	}
	
	// 사업자 로우 하나 불러오기용
	@Override
	public Accom accomSelect(String biz_id) {
		log.info("비즈서비스01 accomSelect 시작...");
		Accom accom = sd01.accomSelect(biz_id);
		return accom;
	}
	
	// 업데이트문에 사용
	@Override
	public void accomUpdate(Accom accom) {
		log.info("비즈서비스01 accomUpdate 시작...");
		sd01.accomUpdate(accom);
	}
	
	// 이름은 삭제(delete)지만 실제로 로우를 삭제하는건 아님
	@Override
	public void accomDelete(String biz_id) {
		log.info("비즈서비스01 accomDelete 시작...");
		sd01.accomDelete(biz_id);
	}

	// 계정정보변경을 위해 비밀번호 검증, 아이디에 매칭되는 실제 비밀번호를 리턴할것
	@Override
	public String getDBPassword(String user_id) {
		log.info("비즈서비스01 getDBPassword 시작...");
		return sd01.getDBPassword(user_id);
	}
	
	// 아이디로 현재로그인한 유저의 전체로우 가져옴
	@Override
	public Users getNowLoginUser(String user_id) {
		log.info("비즈서비스01 getNowLoginUser 시작...");
		return sd01.getNowLoginUser(user_id);
	}


	// 실제로 삭제 X
	@Override
	public int userDelete(String user_id) {
		log.info("비즈서비스01 userDelete 시작...");
		return sd01.userDelete(user_id);
	}


	@Override
	public void userUpdate(Users user) {
		log.info("비즈서비스01 userUpdate 시작...");
		sd01.userUpdate(user);
	}


	@Override
	public List<Room> roomList(String user_id) {
		log.info("비즈서비스01 roomList 시작...");
		List<Room> roomList = sd01.roomList(user_id);
		return roomList;
	}


	@Override
	public void roomInsert(Room room) {
		log.info("비즈서비스01 roomInsert 시작...");
		//방번호 추출하기
		int maxR_id = sd01.roomIdExtract(room);
		maxR_id +=1;
		room.setR_id(maxR_id);
		sd01.roomInsert(room);
	}





}
