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
	
	@Override
	public List<Accom> cgGetProductByAccomtype(String accom_type) {
		System.out.println("서비스 cgProductByAccomtype 시작");
		List<Accom> cgProductLists = dao.cgGetProductByAccomtype(accom_type);
		List<Room_Img> cgRoom_img = dao.cgGetRoom_imgWithoutHotel();
		
		// biz_id로 연결될 친구들이 들어갈 리스트 객체
		List<Accom> cgProductListswithoutHotel = new ArrayList<Accom>();
		
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
			cgProductListswithoutHotel.add(accom);
		}
		return cgProductListswithoutHotel;
	}

	@Override
	public List<Accom> cgGetProductHotelLists() {
		System.out.println("서비스 cgProductHotelList 시작");
		List<Accom> cgProductHotelList = dao.cgGetProductHotelLists();
		List<Room_Img> cgHotelImg = dao.cgGetHotelImg();
		
		// biz_id로 연결될 친구들이 들어갈 리스트 객체
		List<Accom> cgProductHotelLists = new ArrayList<Accom>();
		
		// ACCOM 리스트랑   IMG랑  biz_id가 일치하면 이어주기.
		// 2중 for문임
		for (Accom accom : cgProductHotelList) {
			
			for (Room_Img room_Img : cgHotelImg) {
				// 이미지의 biz_id와  accom의 biz_id가 같다면  accom객체가 가지고잇는 img에 넣어주기;
				if (accom.getBiz_id().equals(room_Img.getBiz_id())) {
					accom.setR_img(room_Img.getR_img());
					
				}
			}
			// 최종으로 넣은걸 리스트로 만들어서 최종 리스트가 되는거임
			cgProductHotelLists.add(accom);
		}
		
		return cgProductHotelLists;
	}

	@Override
	public List<Accom> cgGetProductListsByAddr(Accom accom) {
		List<Accom> cgProductListByAddr = dao.cgGetProductListsByAddr(accom);
		List<Room_Img> cgGetRoomImg = dao.cgGetRoom_imgWithoutHotel();
		
		List<Accom> cgProductListsByAddr = new ArrayList<Accom>();
		
		for(Accom accom2 : cgProductListByAddr) {
			
			for(Room_Img room_Img : cgGetRoomImg) {
				if(accom2.getBiz_id().equals(room_Img.getBiz_id())) {
					accom2.setR_img(room_Img.getR_img());
				}
			}
			cgProductListsByAddr.add(accom2);
		}
		return cgProductListsByAddr;
	}



	@Override
	public List<Accom> cgGetProductHotelListsByAddr(String addr) {
		System.out.println("서비스 cgProductHotelListsByAddr 시작");
		List<Accom> cgProductHotelListByAddr = dao.cgGetProductHotelListsByAddr(addr);
		List<Room_Img> cgHotelImg = dao.cgGetHotelImg();
		
		List<Accom> cgProductHotelListsByAddr = new ArrayList<Accom>();
		
		for(Accom accom : cgProductHotelListByAddr) {
			
			for(Room_Img room_Img : cgHotelImg) {
				if(accom.getBiz_id().equals(room_Img.getBiz_id())) {
					accom.setR_img(room_Img.getR_img());
				}
			}
			cgProductHotelListsByAddr.add(accom);
		}
		return cgProductHotelListsByAddr;
	}

	// ajax용
	
		@Override
		public List<ChanJoin> cgGetAjaxProductListsWithoutHotel(ChanJoin chanJoin) {
			System.out.println("서비스 cgProductList 시작");
			List<ChanJoin> cgAjaxProductListWithoutHotel = dao.cgGetAjaxProductListsWithoutHotel(chanJoin);
			List<Room_Img> cgAjaxProductImg = dao.cgGetRoom_imgWithoutHotel();
			
			List<ChanJoin> cgAjaxProductListsWithoutHotel = new ArrayList<ChanJoin>();
			
			for(ChanJoin chanJoin2 : cgAjaxProductListWithoutHotel) {
				
				for(Room_Img room_Img : cgAjaxProductImg) {
					if(chanJoin2.getBiz_id().equals(room_Img.getBiz_id())) {
						chanJoin2.setR_img(room_Img.getR_img());
						
					}
				}
				cgAjaxProductListsWithoutHotel.add(chanJoin2);
				System.out.println("서비스 cgAjaxProductListWithoutHotel 사이즈 2가 나와야됨-> "+cgAjaxProductListsWithoutHotel.size());
			}
			
			return cgAjaxProductListsWithoutHotel;
		}


		@Override
		public List<ChanJoin> cgGetAjaxProductHotelLists(ChanJoin chanJoin) {
			System.out.println("서비스 cgProductHotelList 시작");
			List<ChanJoin> cgAjaxProductHotelList = dao.cgGetAjaxProductHotelLists(chanJoin);
			List<Room_Img> cgAjaxHotelProductImg = dao.cgGetHotelImg();
			
			List<ChanJoin> cgAjaxProductHotelLists = new ArrayList<ChanJoin>();
			
			for(ChanJoin chanJoin2 : cgAjaxProductHotelList) {
				
				for(Room_Img room_Img : cgAjaxHotelProductImg) {
					if(chanJoin2.getBiz_id().equals(room_Img.getBiz_id()))
						chanJoin2.setR_img(room_Img.getR_img());
				}
				
				cgAjaxProductHotelLists.add(chanJoin2);
			}
			
			return cgAjaxProductHotelLists;
		}
	
}
