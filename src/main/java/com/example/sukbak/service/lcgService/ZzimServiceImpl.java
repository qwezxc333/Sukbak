package com.example.sukbak.service.lcgService;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.sukbak.dao.lcgDao.ZzimDao;
import com.example.sukbak.model.Accom;
import com.example.sukbak.model.Room_Img;
import com.example.sukbak.model.Zzim;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ZzimServiceImpl implements ZzimService {
	private final ZzimDao dao;
	
	// 원화로 바꿔주는 메소드 
	public String formatPrice(int price) {
		// 한국으로 위치 지정
	    Locale locale = new Locale("ko", "KR");
	    // NumberFormat.getCurrencyInstance(locale); 으로 화폐 포멧을 한국으로 변경함.
	    NumberFormat formattedPrice = NumberFormat.getCurrencyInstance(locale);
	    // 포멧 한걸 다시 string 타입에 넣어주고 원화 표시를 없앤채로 리턴
	    String formattedPriceWithoutW = formattedPrice.format(price);
	    return formattedPriceWithoutW.replace("₩", "");
	}


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
			
			 int price = Integer.parseInt(accom.getMin_price_r2());
			 // 값을 뽑아와서 인트로 바꾼 후 포멧팅 메소드로 변환 후에 다시 세팅을 해준다. 
		     String formattedPrice = formatPrice(price);
		     accom.setMin_price_r2(formattedPrice);
			
			
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


	@Override
	public List<Accom> getAjaxMyAccomZzimListsByUser_id(String user_id) {
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
