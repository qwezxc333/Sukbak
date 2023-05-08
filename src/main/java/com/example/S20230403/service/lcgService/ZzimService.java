package com.example.S20230403.service.lcgService;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Zzim;

public interface ZzimService {
	// 찜하기
	int cgAjaxInsertZzim(Zzim zzim);
	// 찜 삭제
	int cgAjaxDeleteZzim(Zzim zzim);
	
	// 찜 리스트 가져오기 
	List<Accom> getMyAccomZzimListsByUser_id(String user_id);


}
