package com.example.S20230403.dao.lcgDao;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Zzim;

public interface ZzimDao {
	// 찜하기
	int cgAjaxInsertZzim(Zzim zzim);
	
	// 찜삭제
	int cgAjaxDeleteZzim(Zzim zzim);
	
	// 상세숙소 페이지에서 찜한거 안한거 구분하기
	List<Zzim> getMyZzim(String user_id);
	
	// 유저 마이페이지에서 찜한 숙소만 가져오기 
	List<Accom> getMyAccomZzimListsByUser_id(String user_id);
	
	// 유저 마이페이지에서 찜한 숙소 이미지만 가져오기 
	List<Room_Img> getMyZzimAccomImgsByUser_id(String user_id);


}
