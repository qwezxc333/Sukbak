package com.example.S20230403.service.lcgService;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.ChanJoin;



public interface ProductsFilterService {
	

	// 리펙토링

	List<Accom> cgGetProductByAccomtype(String accom_type);

	List<Accom> cgGetProductHotelLists();
	
	// 주소로 찾기 

	List<Accom> cgGetProductListsByAddr(Accom accom);

	List<Accom> cgGetProductHotelListsByAddr(String addr);
	
	// ajax에서 사용하는 거
	List<ChanJoin> cgGetAjaxProductListsWithoutHotel(ChanJoin chanJoin);

	List<ChanJoin> cgGetAjaxProductHotelLists(ChanJoin chanJoin);

}
