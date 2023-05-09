package com.example.S20230403.dao.lcgDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.ChanJoin;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductsFilterDaoImpl implements ProductsFilterDao {
	private final SqlSession session;

	// 호텔 빼고 accom_type으로 호텔 리스트 가져오기
		@Override
		public List<Accom> cgGetProductByAccomtype(String accom_type) {
			System.out.println(" 다오 cgProductByAccomtype 시작");
			List<Accom> cgProductListsWithoutHotel = null;
			try {
				cgProductListsWithoutHotel = session.selectList("cgGetProductByAccomtype",accom_type);
			} catch (Exception e) {
				System.out.println(" 다오 cgProductByAccomtype 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return cgProductListsWithoutHotel;
		}
		
		// 호텔만 다가져오기
		
		@Override
		public List<Accom> cgGetProductHotelLists() {
			System.out.println("dao cgGetProductHotelList 시작");
			List<Accom> cgProductHotelLists = null;
			try {
				cgProductHotelLists = session.selectList("cgGetProductHotelLists");
			} catch (Exception e) {
				System.out.println("dao cgGetProductHotelList 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return cgProductHotelLists;
		}
		
		// 호텔 사진빼고 다가져오기
		@Override
		public List<Room_Img> cgGetRoom_imgWithoutHotel() {
			System.out.println("다오 getRoom_img 시작");
			List<Room_Img> cgGetRoom_img = null;
			try {
				cgGetRoom_img = session.selectList("cgGetRoom_img");
			} catch (Exception e) {
				System.out.println("다오 getRoom_img 에러-> "+e.getMessage());
			}
			return cgGetRoom_img;
		}
		
		
		
		// 호텔 이미지만 가져오기
		@Override
		public List<Room_Img> cgGetHotelImg() {
			System.out.println("dao cgGetHotelImg 시작");
			List<Room_Img> cgHotelImg = null;
			try {
				cgHotelImg = session.selectList("cgGetRoom_img");
			} catch (Exception e) {
				System.out.println("dao cgGetHotelImg 에러 -> "+e.getMessage());
				// TODO: handle exception
			}
			return cgHotelImg;
		}
		
		// 주소로 호텔빼고 리스트 가져오기
		@Override
		public List<Accom> cgGetProductListsByAddr(Accom accom) {
			System.out.println("dao cgGetProductListsByAddr 시작");
			List<Accom> cgProductListsByAddr = null;
			try {
				cgProductListsByAddr = session.selectList("cgGetProductListsByAddr", accom);
				System.out.println("dao cgGetProductListsByAddr 사이즈-> "+cgProductListsByAddr.size());
			} catch (Exception e) {
				System.out.println("dao cgGetProductListsByAddr 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return cgProductListsByAddr;
		}
		
		// 주소로 호텔 리스트 가져오기
		@Override
		public List<Accom> cgGetProductHotelListsByAddr(String addr) {
			System.out.println("dao cgGetProductHotelListsByAddr 시작");
			List<Accom> cgProductHotelListsByAddr = null;
			try {
				cgProductHotelListsByAddr = session.selectList("cgGetProductHotelListsByAddr", addr);
			} catch (Exception e) {
				System.out.println("dao cgGetProductHotelListsByAddr 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return cgProductHotelListsByAddr;
		}
		
		// ajax dao 필터링
		@Override
		public List<ChanJoin> cgGetAjaxProductListsWithoutHotel(ChanJoin chanJoin) {
			System.out.println("다오 cgProductList[통합] 시작");
			List<ChanJoin> cgAjaxProductListsWithoutHotel = null;
			try {
				cgAjaxProductListsWithoutHotel = session.selectList("cgGetAjaxProductListsWithoutHotel", chanJoin);
				System.out.println("다오 cgProductList[통합] 침대확인용 2개 나와야됨 ->"+cgAjaxProductListsWithoutHotel.size());
			} catch (Exception e) {
				System.out.println("다오 cgProductList 에러 -> "+e.getMessage());
				// TODO: handle exception
			}
			return cgAjaxProductListsWithoutHotel;
		}

		//ajax dao 호텔만 필터링
		@Override
		public List<ChanJoin> cgGetAjaxProductHotelLists(ChanJoin chanJoin) {
			System.out.println("다오 cgProductHotelList 시작");
			List<ChanJoin> hotelProductLists = null;
			try {
				hotelProductLists = session.selectList("cgGetAjaxProductHotelLists", chanJoin);
			} catch (Exception e) {
				System.out.println("다오 cgProductHotelList 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return hotelProductLists;
		}
}
