package com.example.S20230403.service.lcgService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.dao.lcgDao.ProductsFilterDao;
import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Zzim;
import com.example.S20230403.model.ChanJoin;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductsFilterServiceImpl implements ProductsFilterService {
	private final ProductsFilterDao dao;
	
	// 숙소 타입별로 가져오는 로직
	@Override
	public List<Accom> cgGetProductByAccomtype(Accom accom) {
		// 기존 로직을 재활용하기 위함과 값이 재대로 넘어왔는지 확인하기 위해 객체에 넣어줌.
		String accom_type = accom.getAccom_type();
		String user_id = accom.getUser_id();
//		System.out.println("찜목록 서비스 accom_type-> "+accom_type);
//		System.out.println("찜목록 서비스 user_id-> "+ user_id);
		// accom_type에 맞게 모든 상품을 다 가져오는 로직
		List<Accom> cgProductLists = dao.cgGetProductByAccomtype(accom_type);
		// user_id값이 변하지 않는지 확인.
		user_id = accom.getUser_id();
	//	System.out.println("찜목록 서비스 리스트 가져오고  user_id-> "+ user_id);
		
		// 로그인한 user_id로 찜 테이블에서 리스트 가져오기
		// 로그인을 하지 않았다면 리스트가 없음.
		List<Zzim> zzimLists = dao.getZzimLists(user_id);
		
		// 숙박업소 썸네일 가져오는 로직
		List<Room_Img> cgRoom_img = dao.cgGetRoom_img();
		
		// biz_id로 연결될 친구들이 들어갈 리스트 객체
		List<Accom> cgProductListsByAccomtype = new ArrayList<Accom>();
		
		// 우선 리스트를 하나 하나 꺼내준다.
		for(Accom accom2 : cgProductLists) {
			//user_id가 살아잇는지 확인.
			//System.out.println("이중 for문안에서 유저아이디-> "+user_id);
			
			// 이미지 사진도 꺼내줌
			for(Room_Img room_Img : cgRoom_img) {
				// accom2의 biz_id와 room_Img의 biz_id가 같을 경우 accom2 필드에 이미지를 넣어줌
				if(accom2.getBiz_id().equals(room_Img.getBiz_id())) {
					accom2.setR_img(room_Img.getR_img());
				}
			}
			// 찜 리스트를 리스트로 뽑음. 찜 리스트에는 user_id와 biz_id가 복합키로 되어있기 때문에 두개가 동시에 맞는 것에만 넣어줌.
			// 로그인을 하지 않았다면 뽑을 리스트가 없어서 이 로직은 생략됨.
			for(Zzim zzim : zzimLists) {
				// 만약 zzim의 biz_id와 accom2(업소 biz_id)가 같고, 로그인한 user_id와 찜에 user_id가 같다면 accom2필드에 있는 zzim_status에 'Y'값을 넣어줌
				if(accom2.getBiz_id().equals(zzim.getBiz_id()) && user_id.equals(zzim.getUser_id())) {
					accom2.setZzim_status(zzim.getZzim_status());
				}
			}
			
			cgProductListsByAccomtype.add(accom2);
			
		}
		return cgProductListsByAccomtype;
	}
	

	
	// 주소를 기반으로 가져오는 로직 파라미터에 addr도 들어가야하기 때문에 accom 객체 통채포 보냄.
	@Override
	public List<Accom> cgGetProductListsByAddr(Accom accom) {
		// 기존 로직을 재활용하기 위함과 값이 재대로 넘어왔는지 확인하기 위해 객체에 넣어줌.
		String user_id = accom.getUser_id();
		//System.out.println("주소기반 유저아이디 나와야됨 1 -> "+user_id);
		// accom_type과 addr에 맞게 모든 상품을 다 가져오는 로직
		List<Accom> cgProductLists = dao.cgGetProductListsByAddr(accom);
		
		// 숙박업소 썸네일 가져오는 로직
		List<Room_Img> cgGetRoomImg = dao.cgGetRoom_img();
		//System.out.println("주소기반 유저아이디 나와야됨 2 -> "+user_id);
		
		// 로그인한 user_id로 찜 테이블에서 리스트 가져오기
		// 로그인을 하지 않았다면 리스트가 없음.
		List<Zzim> zzimLists = dao.getZzimLists(user_id);
		
		// biz_id로 연결될 친구들이 들어갈 리스트 객체
		List<Accom> cgProductListsByAddr = new ArrayList<Accom>();
		
		// 우선 리스트를 하나 하나 꺼내준다.
		for(Accom accom2 : cgProductLists) {
			//user_id가 살아잇는지 확인.
			//System.out.println("주소기반 유저아이디 나와야됨 3 -> "+user_id);
			
			// 이미지 사진도 꺼내줌
			for(Room_Img room_Img : cgGetRoomImg) {
				// accom2의 biz_id와 room_Img의 biz_id가 같을 경우 accom2 필드에 이미지를 넣어줌
				if(accom2.getBiz_id().equals(room_Img.getBiz_id())) {
					accom2.setR_img(room_Img.getR_img());
				}
			}
			
			// 찜 리스트를 리스트로 뽑음. 찜 리스트에는 user_id와 biz_id가 복합키로 되어있기 때문에 두개가 동시에 맞는 것에만 넣어줌.
			// 로그인을 하지 않았다면 뽑을 리스트가 없어서 이 로직은 생략됨.
			for(Zzim zzim : zzimLists) {
				// 만약 zzim의 biz_id와 accom2(업소 biz_id)가 같고, 로그인한 user_id와 찜에 user_id가 같다면 accom2필드에 있는 zzim_status에 'Y'값을 넣어줌
				if(accom2.getBiz_id().equals(zzim.getBiz_id()) && user_id.equals(zzim.getUser_id())) {
					accom2.setZzim_status(zzim.getZzim_status());
				}
			}
			cgProductListsByAddr.add(accom2);
		}
		return cgProductListsByAddr;
	}

	// ajax용
	
		@Override
		public List<ChanJoin> cgGetAjaxProductListsByAccomtypeAndAddr(ChanJoin chanJoin) {
			//System.out.println("서비스 cgProductList 시작");
			String user_id = chanJoin.getUser_id();
			//System.out.println("user_id 잘 가져왓나 확인용 -> "+user_id);
			List<ChanJoin> cgAjaxProductLists = dao.cgGetAjaxProductListsByAccomtypeAndAddr(chanJoin);
			List<Room_Img> cgAjaxProductImg = dao.cgGetRoom_img();
			List<Zzim> zzimLists = dao.getZzimLists(user_id);
			List<ChanJoin> cgAjaxProductListsByAccomtypeAndAddr = new ArrayList<ChanJoin>();
			//System.out.println("user_id 잘 가져왓나 확인용 2-> "+user_id);
			
			for(ChanJoin chanJoin2 : cgAjaxProductLists) {
				//System.out.println("user_id 잘 가져왓나 확인용 foreach -> "+user_id);
				
				for(Room_Img room_Img : cgAjaxProductImg) {
					if(chanJoin2.getBiz_id().equals(room_Img.getBiz_id())) {
						chanJoin2.setR_img(room_Img.getR_img());	
					}
				}
				
				// 찜 리스트를 리스트로 뽑음. 찜 리스트에는 user_id와 biz_id가 복합키로 되어있기 때문에 두개가 동시에 맞는 것에만 넣어줌.
				// 로그인을 하지 않았다면 뽑을 리스트가 없어서 이 로직은 생략됨.
				for(Zzim zzim : zzimLists) {
					// 만약 zzim의 biz_id와 accom2(업소 biz_id)가 같고, 로그인한 user_id와 찜에 user_id가 같다면 accom2필드에 있는 zzim_status에 'Y'값을 넣어줌
					if(chanJoin2.getBiz_id().equals(zzim.getBiz_id()) && user_id.equals(zzim.getUser_id())) {
						chanJoin2.setZzim_status(zzim.getZzim_status());
					}
				}
				cgAjaxProductListsByAccomtypeAndAddr.add(chanJoin2);
				//System.out.println("서비스 cgAjaxProductListWithoutHotel 사이즈 2가 나와야됨-> "+cgAjaxProductListsByAccomtypeAndAddr.size());
			}
			
			return cgAjaxProductListsByAccomtypeAndAddr;
		}
		
		
		

	
}
