package com.example.sukbak.dao.lcgDao;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.Room_Img;
import com.example.sukbak.model.Zzim;
import com.example.sukbak.model.ChanJoin;



public interface ProductsFilterDao {
	
	// 리펙토링 숙소 종류로 가져오기
	List<Accom> cgGetProductByAccomtype(String accom_type);

	List<Room_Img> cgGetRoom_img();

	// 주소로 가져오기

	List<Accom> cgGetProductListsByAddr(Accom accom);

	
	// ajax에서 사용
	
	List<ChanJoin> cgGetAjaxProductListsByAccomtypeAndAddr(ChanJoin chanJoin);

	// 찜목록 가져오기
	List<Zzim> getZzimLists(String user_id);
	
		

}
