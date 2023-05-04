package com.example.S20230403.service.lcgService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.dao.lcgDao.ProductsFilterDao;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.ChanJoin;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductsFilterServiceImpl implements ProductsFilterService {
	private final ProductsFilterDao dao;
	
	// 숙소 타입별로 가져오는 로직
	@Override
	public List<Accom> cgGetProductByAccomtype(String accom_type) {
		System.out.println("서비스 cgProductByAccomtype 시작");
		List<Accom> cgProductLists = dao.cgGetProductByAccomtype(accom_type);
		List<Room_Img> cgRoom_img = dao.cgGetRoom_img();
		
		// biz_id로 연결될 친구들이 들어갈 리스트 객체
		List<Accom> cgProductListsByAccomtype = new ArrayList<Accom>();
		
		// ACCOM 리스트랑   IMG랑  biz_id가 일치하면 이어주기.
		// 2중 for문임
		for(Accom accom : cgProductLists) {
			
			for(Room_Img room_Img : cgRoom_img) {
				// 이미지의 biz_id와  accom의 biz_id가 같다면  accom객체가 가지고잇는 img에 넣어주기;
				if(accom.getBiz_id().equals(room_Img.getBiz_id())) {				
					accom.setR_img(room_Img.getR_img());
				}
			}
			// 최종으로 넣은걸 리스트로 만들어서 최종 리스트가 되는거임
			cgProductListsByAccomtype.add(accom);
		}
		return cgProductListsByAccomtype;
	}
	
	// 주소를 기반으로 가져오는 로직
	@Override
	public List<Accom> cgGetProductListsByAddr(Accom accom) {
		List<Accom> cgProductLists = dao.cgGetProductListsByAddr(accom);
		List<Room_Img> cgGetRoomImg = dao.cgGetRoom_img();
		
		List<Accom> cgProductListsByAddr = new ArrayList<Accom>();
		
		for(Accom accom2 : cgProductLists) {
			
			for(Room_Img room_Img : cgGetRoomImg) {
				if(accom2.getBiz_id().equals(room_Img.getBiz_id())) {
					accom2.setR_img(room_Img.getR_img());
				}
			}
			cgProductListsByAddr.add(accom2);
		}
		return cgProductListsByAddr;
	}

	// ajax용
	
		@Override
		public List<ChanJoin> cgGetAjaxProductListsByAccomtypeAndAddr(ChanJoin chanJoin) {
			System.out.println("서비스 cgProductList 시작");
			List<ChanJoin> cgAjaxProductLists = dao.cgGetAjaxProductListsByAccomtypeAndAddr(chanJoin);
			List<Room_Img> cgAjaxProductImg = dao.cgGetRoom_img();
			
			List<ChanJoin> cgAjaxProductListsByAccomtypeAndAddr = new ArrayList<ChanJoin>();
			
			for(ChanJoin chanJoin2 : cgAjaxProductLists) {
				
				for(Room_Img room_Img : cgAjaxProductImg) {
					if(chanJoin2.getBiz_id().equals(room_Img.getBiz_id())) {
						chanJoin2.setR_img(room_Img.getR_img());
						
					}
				}
				cgAjaxProductListsByAccomtypeAndAddr.add(chanJoin2);
				System.out.println("서비스 cgAjaxProductListWithoutHotel 사이즈 2가 나와야됨-> "+cgAjaxProductListsByAccomtypeAndAddr.size());
			}
			
			return cgAjaxProductListsByAccomtypeAndAddr;
		}

	
}
