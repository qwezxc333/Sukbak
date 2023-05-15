package com.example.S20230403.dao.lcgDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Zzim;
import com.example.S20230403.model.ChanJoin;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductsFilterDaoImpl implements ProductsFilterDao {
	private final SqlSession session;

		// 호텔 빼고 accom_type으로 호텔 리스트 가져오기
		@Override
		public List<Accom> cgGetProductByAccomtype(String accom_type) {
			//System.out.println(" 다오 cgProductByAccomtype 시작");
			List<Accom> cgProductLists = null;
			try {
				cgProductLists = session.selectList("cgGetProductByAccomtype",accom_type);
			} catch (Exception e) {
				//System.out.println(" 다오 cgProductByAccomtype 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return cgProductLists;
		}
		
		
		// 호텔 사진빼고 다가져오기
		@Override
		public List<Room_Img> cgGetRoom_img() {
			//System.out.println("다오 getRoom_img 시작");
			List<Room_Img> cgGetRoom_img = null;
			try {
				cgGetRoom_img = session.selectList("cgGetRoom_img");
			} catch (Exception e) {
				//System.out.println("다오 getRoom_img 에러-> "+e.getMessage());
			}
			return cgGetRoom_img;
		}
		
		
		// 주소로 호텔빼고 리스트 가져오기
		@Override
		public List<Accom> cgGetProductListsByAddr(Accom accom) {
			//System.out.println("dao cgGetProductListsByAddr 시작");
			List<Accom> cgProductListsByAddr = null;
			try {
				cgProductListsByAddr = session.selectList("cgGetProductListsByAddr", accom);
				//System.out.println("dao cgGetProductListsByAddr 사이즈-> "+cgProductListsByAddr.size());
			} catch (Exception e) {
				//System.out.println("dao cgGetProductListsByAddr 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return cgProductListsByAddr;
		}
		
		
		// ajax dao 필터링
		@Override
		public List<ChanJoin> cgGetAjaxProductListsByAccomtypeAndAddr(ChanJoin chanJoin) {
			//System.out.println("다오 cgProductList[통합] 시작");
			List<ChanJoin> cgAjaxProductLists = null;
			try {
				//cgAjaxProductListsWithoutHotel = session.selectList("cgGetAjaxProductListsWithoutHotel", chanJoin);
				cgAjaxProductLists = session.selectList("cgGetAjaxProductListsByAccomtypeAndAddr", chanJoin);
				//System.out.println("다오 cgProductList[통합] 침대확인용 2개 나와야됨 ->"+cgAjaxProductLists.size());
			} catch (Exception e) {
				//System.out.println("다오 cgProductList 에러 -> "+e.getMessage());
				// TODO: handle exception
			}
			return cgAjaxProductLists;
		}


		@Override
		public List<Zzim> getZzimLists(String user_id) {
			//System.out.println("다오 getZzimLists시작");
			List<Zzim> zzimLists = null;
			try {
				zzimLists = session.selectList("getZzimLists", user_id);
			} catch (Exception e) {
				//System.out.println("getZzimLists 에러-> "+e.getMessage());
				// TODO: handle exception
			}
			return zzimLists;
		}

}
