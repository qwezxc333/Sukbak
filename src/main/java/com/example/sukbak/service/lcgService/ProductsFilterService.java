package com.example.sukbak.service.lcgService;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.ChanJoin;



public interface ProductsFilterService {
	

	// 리펙토링
	List<Accom> cgGetProductByAccomtype(Accom accom);

	
	// 주소로 찾기 

	List<Accom> cgGetProductListsByAddr(Accom accom);

	
	// ajax에서 사용하는 거
	List<ChanJoin> cgGetAjaxProductListsByAccomtypeAndAddr(ChanJoin chanJoin);
	


}
