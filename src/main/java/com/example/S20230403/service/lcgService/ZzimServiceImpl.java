package com.example.S20230403.service.lcgService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.S20230403.dao.lcgDao.ZzimDao;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Zzim;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ZzimServiceImpl implements ZzimService {
	private final ZzimDao dao;


	// 찜하기
	@Override
	public int cgAjaxInsertZzim(Zzim zzim) {
		int result = dao.cgAjaxInsertZzim(zzim);
		return result;
	}
	// 찜 삭제
	@Override
	public int cgAjaxDeleteZzim(Zzim zzim) {
		int result = dao.cgAjaxDeleteZzim(zzim);
		return result;
	}
	
	// 내 아이디로 찜 되어 있는 목록만 가져오기 
	// cgGetProductByAccomtype는 accomLists에 찜 여부를 붙이지만 이건 찜리스트에 AccomLists를 붙임
	@Override
	public List<Accom> getMyAccomZzimListsByUser_id(String user_id) {
		//System.out.println("서비스 getMyAccomZzimLists 시작");
		// 내 아이디로 찜 되어 있는 목록만 가져오기 
		List<Accom> myAccomZzimLists = dao.getMyAccomZzimListsByUser_id(user_id);
		//System.out.println("찜 리스트 사이즈 3나와야됨 -> "+myAccomZzimLists.size());
		
		// accom 썸네일 가저오기 , user_id로 내가 찜한 숙소의 사진만 가져옴..
		List<Room_Img> myZzimAccomImgsByUser_id = dao.getMyZzimAccomImgsByUser_id(user_id);
		//System.out.println("숙소 사진 사이즈 3나와야됨 -> "+myZzimAccomImgsByUser_id.size());
		
		// 최종 결과물 담을 arraylist 생성
		List<Accom> myAccomZzimListsByUser_id = new ArrayList<Accom>();
		
		// for문으로 
		// 우선 리스트를 하나 하나 꺼내준다.
		for(Accom accom : myAccomZzimLists) {
			//biz_id가 살아잇는지 확인.
			//System.out.println("이중 for문안에서 biz_id-> "+accom.getBiz_id());
			
			// 이미지 사진도 꺼내줌
			for(Room_Img room_Img : myZzimAccomImgsByUser_id) {
				// accom2의 biz_id와 room_Img의 biz_id가 같을 경우 accom2 필드에 이미지를 넣어줌
				if(accom.getBiz_id().equals(room_Img.getBiz_id())) {
					accom.setR_img(room_Img.getR_img());
				}
			}
			
			myAccomZzimListsByUser_id.add(accom);
			
		}
		
		
		return myAccomZzimListsByUser_id;
	}
	
}
