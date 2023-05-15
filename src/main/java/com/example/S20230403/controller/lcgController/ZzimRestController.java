package com.example.S20230403.controller.lcgController;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Zzim;
import com.example.S20230403.service.lcgService.ZzimService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ZzimRestController {
	private final ZzimService service;
	
	@GetMapping("/cgAjaxInsertZzim")
	public int cgAjaxInsertZzim(Zzim zzim) {
		//System.out.println("cgAjaxInsertZzim 시작");
		int result = 0;
//		System.out.println(zzim.getAuth());
//		System.out.println(zzim.getUser_id());
//		System.out.println(zzim.getBiz_id());
		// 혹시몰라서 여기서 한번 더 보안
		if(zzim.getUser_id() == null || zzim.getUser_id() == "" || !zzim.getAuth().equals("[USER]")) {
			return 0;
		}
		
		result = service.cgAjaxInsertZzim(zzim);
		//System.out.println("찜결과 1이 나와야됨-> "+result);
		
		return result;
	}
	
	@GetMapping("/cgAjaxDeletetZzim")
	public int cgAjaxDeletetZzim(Zzim zzim) {
		//System.out.println("cgAjaxInsertZzim 시작");
		int result = 0;
//		System.out.println(zzim.getAuth());
//		System.out.println(zzim.getUser_id());
//		System.out.println(zzim.getBiz_id());
		// 혹시몰라서 여기서 한번 더 보안
		if(zzim.getUser_id() == null || zzim.getUser_id() == "" || !zzim.getAuth().equals("[USER]")) {
			return 0;
		}
		
		result = service.cgAjaxDeleteZzim(zzim);
		//System.out.println("컨트롤러 찜삭제결과 1이 나와야됨-> "+result);
		
		return result;
	}
	
	@GetMapping("cgAjaxZzimListsByUser_id")
	public List<Accom> cgAjaxGetZzimListsByUser_id(@RequestParam("user_id") String user_id){
		
		List<Accom> cgAjaxZzimListsByUser_id = service.getMyAccomZzimListsByUser_id(user_id);
		
		return cgAjaxZzimListsByUser_id;
	}
}
