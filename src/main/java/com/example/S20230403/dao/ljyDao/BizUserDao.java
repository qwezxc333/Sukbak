package com.example.S20230403.dao.ljyDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Comm;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@AllArgsConstructor
public class BizUserDao implements SukbakDao{

	private final SqlSession sqlSession;
	
	@Override
	public void accomInsert(Accom accom) {
		log.info("비즈다오01 insertCompanyInfo 시작...");
		log.info(accom.toString());
		try {
			sqlSession.insert("accomInsert", accom);
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Accom> accomList(String user_id) {
		log.info("비즈다오01 accomList 시작...");
		log.info("비즈다오01 user_id -> {}", user_id);
		try {
			List<Accom> accomList = sqlSession.selectList("accomList", user_id);
			return accomList;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
		}	
	}

	@Override
	public List<Comm> selectCommList(int code) {
		log.info("비즈다오01 selectCommList 시작...");
		try {
			List<Comm> stats = sqlSession.selectList("selectCommList",code);
			return stats;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
		}
	}
	
	// 아이디로 유저 로우 하나 가져옴, 일단 셀렉트, 업데이트문에서 사용
	@Override
	public Accom accomSelect(String biz_id) {
		log.info("비즈다오01 accomSelect 시작...");
		try {
			Accom accom = sqlSession.selectOne("accomSelect", biz_id);
			return accom;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public void accomUpdate(Accom accom) {
		log.info("비즈다오01 accomSelect 시작...");
		try {
			sqlSession.selectOne("accomUpdate", accom);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	// 아이디받아서 해당 로우 삭제(비활성화) 실제로 삭제하지는 않는다.
	@Override
	public void accomDelete(String biz_id) {
		log.info("비즈다오01 accomDelete 시작...");
		try {
			sqlSession.update("accomDelete", biz_id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String getDBPassword(String user_id) {
		log.info("비즈다오01 getDBPassword 시작...");
		try {
			return sqlSession.selectOne("getDBPassword", user_id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Users getNowLoginUser(String user_id) {
		log.info("비즈다오01 getNowLoginUser 시작...");
		try {
			return sqlSession.selectOne("getNowLoginUser", user_id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int userDelete(String user_id) {
		log.info("비즈다오01 userDelete 시작...");
		try {
			System.out.println("비즈다오01 userDelete user_id -> "+ user_id);
			return sqlSession.update("userDelete", user_id);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public void userUpdate(Users user) {
		log.info("비즈다오01 userUpdate 시작...");
		try {
			System.out.println("비즈다오01 userDelete user_id -> "+ user);
			int userUpdateResult = sqlSession.update("userUpdate", user);
			System.out.println("비즈다오01 userUpdate userDeleteResult -> "+userUpdateResult);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<Room> roomList(String user_id) {log.info("비즈다오01 userUpdate 시작...");
		try {
			System.out.println("비즈다오01 roomList user_id -> "+ user_id);
			List<Room> roomList = sqlSession.selectList("roomList", user_id); 
			return roomList;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void roomInsert(Room room) {
		try {
			System.out.println("비즈다오01 roomInsert room -> "+ room);
			sqlSession.insert("roomInsert", room); 
		} catch (Exception e) {
			throw e;
		}
		
	}

	// roomInsert 위해서 방번호 추출
	@Override
	public int roomIdExtract(Room room) {
		try {
			System.out.println("비즈다오01 roomIdExtract room -> "+ room);
			String biz_id = room.getBiz_id();
			System.out.println("비즈다오01 roomIdExtract biz_id -> "+ biz_id);
			int maxR_id = sqlSession.selectOne("roomIdExtract", biz_id); 
			return maxR_id;
		} catch (Exception e) {
			throw e;
		}
		
	}

	
}
