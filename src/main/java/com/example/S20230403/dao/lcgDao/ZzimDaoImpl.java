package com.example.S20230403.dao.lcgDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Zzim;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ZzimDaoImpl implements ZzimDao {
	private final SqlSession session;

	@Override
	public int cgAjaxInsertZzim(Zzim zzim) {
	//	System.out.println("cgAjaxInsertZzim 시작");
		int result = 0;
		try {
			result = session.insert("cgAjaxInsertZzim", zzim);
		} catch (Exception e) {
		//	System.out.println("cgAjaxInsertZzim 에러-> "+e.getMessage());
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public int cgAjaxDeleteZzim(Zzim zzim) {
		//System.out.println("dao cgAjaxDeleteZzim 시작");
		int result = 0;
		try {
			result = session.delete("cgAjaxDeleteZzim", zzim);
			//System.out.println("dao cgAjaxDeleteZzim result 1나와야돼 -> "+result);
		} catch (Exception e) {
			//System.out.println("cgAjaxDeleteZzim 에러 -> "+e.getMessage());
			// TODO: handle exception
		}
		return result;
	}
	// 상세숙소 페이지에서 찜한거 안한거 구분하기
	@Override
	public List<Zzim> getMyZzim(String user_id) {
		//System.out.println("다오 getMyZzim 시작");
		List<Zzim> myZzimLists = null;
		try {
			myZzimLists = session.selectList("getZzimLists", user_id);
			//System.out.println("다오 getMyZzim 리스트-> "+myZzimLists.size());
		} catch (Exception e) {
			//System.out.println("다오 getMyZzim 에러 -> "+e.getMessage());
			// TODO: handle exception
		}
		return myZzimLists;
	}
	// 유저 마이페이지에서 찜한 숙소만 가져오기 
	@Override
	public List<Accom> getMyAccomZzimListsByUser_id(String user_id) {
		//System.out.println("dao getMyAccomZzimLists 시작");
		List<Accom> myAccomZzimLists = null;
		try {
			myAccomZzimLists = session.selectList("getMyAccomZzimListsByUser_id", user_id);
			//System.out.println("dao getMyAccomZzimLists 사이즈 -> "+myAccomZzimLists.size());
		} catch (Exception e) {
			//System.out.println("dao getMyAccomZzimLists 에러-> "+e.getMessage());
			// TODO: handle exception
			
		}
		return myAccomZzimLists;
	}
	//// 유저 마이페이지에서 찜한 숙소 이미지만 가져오기 
	@Override
	public List<Room_Img> getMyZzimAccomImgsByUser_id(String user_id) {
		//System.out.println("dao getMyZzimAccomImgsByUser_id 시작");
		List<Room_Img> myZzimAccomImgsByUser_id = null;
		try {
			myZzimAccomImgsByUser_id = session.selectList("myZzimAccomImgsByUser_id", user_id);
			//System.out.println("dao getMyZzimAccomImgsByUser_id 사이즈 -> "+myZzimAccomImgsByUser_id.size());
		} catch (Exception e) {
			//System.out.println("dao getMyZzimAccomImgsByUser_id 에러 -> "+e.getMessage());
			// TODO: handle exception
		}
		return myZzimAccomImgsByUser_id;
	}

	
}
