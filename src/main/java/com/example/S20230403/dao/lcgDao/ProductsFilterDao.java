package com.example.S20230403.dao.lcgDao;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.ChanJoin;



public interface ProductsFilterDao {
	
	// 리펙토링 호텔빼고 가져오기
	List<Accom> cgGetProductByAccomtype(String accom_type);

	List<Room_Img> cgGetRoom_imgWithoutHotel();


	// 리펙토링 호텔만 가져오기
	List<Accom> cgGetProductHotelLists();

	List<Room_Img> cgGetHotelImg();
	
	// 주소로 가져오기

	List<Accom> cgGetProductListsByAddr(Accom accom);

	List<Accom> cgGetProductHotelListsByAddr(String addr);
	
	// ajax에서 사용
	
	List<ChanJoin> cgGetAjaxProductListsWithoutHotel(ChanJoin chanJoin);
	
	List<ChanJoin> cgGetAjaxProductHotelLists(ChanJoin chanJoin);
		
		

}
