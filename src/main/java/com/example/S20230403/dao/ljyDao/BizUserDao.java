package com.example.S20230403.dao.ljyDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Comm;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Room_Img;
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
		//log.info("비즈다오01 insertCompanyInfo 시작...");
		//log.info(accom.toString());
		try {
			sqlSession.insert("accomInsert", accom);
		} catch (Exception e) {
			//log.info(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Accom> accomList(String user_id) {
		//log.info("비즈다오01 accomList 시작...");
		//log.info("비즈다오01 user_id -> {}", user_id);
		try {
			List<Accom> accomList = sqlSession.selectList("accomList", user_id);
			return accomList;
		} catch (Exception e) {
			//log.info(e.getMessage());
			throw e;
		}	
	}

	@Override
	public List<Comm> selectCommList(int code) {
		//log.info("비즈다오01 selectCommList 시작...");
		try {
			List<Comm> stats = sqlSession.selectList("selectCommList",code);
			return stats;
		} catch (Exception e) {
			//log.info(e.getMessage());
			throw e;
		}
	}
	
	// 아이디로 유저 로우 하나 가져옴, 일단 셀렉트, 업데이트문에서 사용
	@Override
	public Accom accomSelect(String biz_id) {
		//log.info("비즈다오01 accomSelect 시작...");
		try {
			Accom accom = sqlSession.selectOne("accomSelect", biz_id);
			return accom;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public void accomUpdate(Accom accom) {
		//log.info("비즈다오01 accomSelect 시작...");
		try {
			sqlSession.selectOne("accomUpdate", accom);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public String getDBPassword(String user_id) {
		//log.info("비즈다오01 getDBPassword 시작...");
		try {
			return sqlSession.selectOne("getDBPassword", user_id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Users getNowLoginUser(String user_id) {
		//log.info("비즈다오01 getNowLoginUser 시작...");
		try {
			return sqlSession.selectOne("getNowLoginUser", user_id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int userDelete(String user_id) {
		//log.info("비즈다오01 userDelete 시작...");
		try {
			//System.out.println("비즈다오01 userDelete user_id -> "+ user_id);
			return sqlSession.update("userDelete", user_id);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public void userUpdate(Users user) {
		//log.info("비즈다오01 userUpdate 시작...");
		try {
			//System.out.println("비즈다오01 userDelete user_id -> "+ user);
			int userUpdateResult = sqlSession.update("userUpdate", user);
			//System.out.println("비즈다오01 userUpdate userDeleteResult -> "+userUpdateResult);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<Room> roomList(String user_id) {//log.info("비즈다오01 userUpdate 시작...");
		try {
			//System.out.println("비즈다오01 roomList user_id -> "+ user_id);
			List<Room> roomList = sqlSession.selectList("roomList", user_id); 
			return roomList;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void roomInsert(Room room) {
		try {
			//System.out.println("비즈다오01 roomInsert room -> "+ room);
			sqlSession.insert("roomInsert", room); 
		} catch (Exception e) {
			throw e;
		}
		
	}

	// roomInsert 위해서 방번호 추출
	@Override
	public int roomIdExtract(Room room) {
		try {
			//System.out.println("비즈다오01 roomIdExtract room -> "+ room);
			String biz_id = room.getBiz_id();
			//System.out.println("비즈다오01 roomIdExtract biz_id -> "+ biz_id);
			Integer maxR_id = sqlSession.selectOne("roomIdExtract", biz_id); 
			//System.out.println("비즈다오01 roomIdExtract  maxR_id -> "+maxR_id);
			if (maxR_id == null) {
				maxR_id = 0;
				//System.out.println("비즈다오01 roomIdExtract was null, set 0");
			}
			return maxR_id;
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public int getImgNum(Room room) {
		try {
			//System.out.println("비즈다오01 getImgNum room -> "+ room);
			Integer imgNum =  sqlSession.selectOne("getImgNum", room);
			if (imgNum == null) {
				imgNum = 0;		
			}
			//System.out.println("비즈다오01 getImgNum imgNum -> "+imgNum);
			return imgNum;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void roomImgInsert(Room room) {
		try {
			//System.out.println("비즈다오01 roomImgInsert room -> "+ room);
			sqlSession.insert("roomImgInsert", room);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<Room> selectRoomImgList(Room_Img ri) {
		try {
			//System.out.println("비즈다오01 selectRoomImgList ri -> "+ ri);
			return sqlSession.selectList("selectRoomImgList", ri);
		} catch (Exception e) {
			throw e;
		}		
	}

	@Override
	public Room roomSelect(Room room) {
		try {
			//System.out.println("비즈다오01 roomSelect room -> "+ room);
			return sqlSession.selectOne("roomSelect", room);
		} catch (Exception e) {
			throw e;
		}	
	}

	@Override
	public void roomImgDelete(Room room) {
		try {
			//System.out.println("비즈다오01 roomImgDelete room -> "+ room);
			sqlSession.delete("roomImgDelete", room);
		} catch (Exception e) {
			throw e;
		}	
		
	}

	@Override
	public void roomUpdate(Room room) {
		try {
			//System.out.println("비즈다오01 roomUpdate room -> "+ room);
			sqlSession.update("roomUpdate", room);
		} catch (Exception e) {
			throw e;
		}	
		
		
	}

	@Override
	public void roomStatus(Room room, String string) {
		try {
			//System.out.println("비즈다오01 roomStatus room -> "+ room);
			//System.out.println("비즈다오01 roomStatus string -> "+ string);
			switch (string) {
				case "hidden" :
				//System.out.println("비즈다오01 roomStatus roomHidden 시작");
				sqlSession.update("roomHidden", room);
				break;
				case "open" :
				//System.out.println("비즈다오01 roomStatus roomOpen 시작");
				sqlSession.update("roomOpen", room);
				break;
				case "delete" :
				//System.out.println("비즈다오01 roomStatus roomDelete 시작");
				sqlSession.update("roomDelete", room);
				break;
			}		
		} catch (Exception e) {
			throw e;
		}	
		
	}

	@Override
	public boolean getBizId(String biz_id) {
		try {
			//System.out.println("비즈다오01 getBizId biz_id -> "+ biz_id);
			int result = sqlSession.selectOne("getBizId", biz_id);
			//System.out.println("비즈다오01 getBizId result ->"+result);
			return !(result > 0);
		} catch (Exception e) {
			throw e;
		}	
	}

	@Override
	public void updateRoomCount(String biz_id, String string) {
		try {
			//System.out.println("비즈다오01 updateRoomCount biz_id -> "+ biz_id);
			//System.out.println("비즈다오01 updateRoomCount string -> "+ string);
			switch (string) {
				case "update" :
				//System.out.println("비즈다오01 updateRoomCount roomCountUpdate 시작");
				sqlSession.update("roomCountUpdate", biz_id);
				break;
				
				case "minus" :
				//System.out.println("비즈다오01 updateRoomCount roomCountMinus 시작");
				sqlSession.update("roomCountMinus", biz_id);
				break;
			}		
		} catch (Exception e) {
			throw e;
		}	
		
	}

	@Override
	public void accomStatus(Accom accom, String string) {
		try {
			//System.out.println("비즈다오01 accomStatus accom -> "+ accom);
			//System.out.println("비즈다오01 accomStatus string -> "+ string);
			switch (string) {
				case "hidden" :
				//System.out.println("비즈다오01 accomStatus roomHidden 시작");
				sqlSession.update("accomHidden", accom);
				break;
				case "open" :
				//System.out.println("비즈다오01 accomStatus roomOpen 시작");
				sqlSession.update("accomOpen", accom);
				break;
				case "delete" :
				//System.out.println("비즈다오01 accomStatus roomDelete 시작");
				sqlSession.update("accomDelete", accom);
				break;
			}		
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<Room> roomListSelectWithAccom(String biz_id) {
		try {
			//System.out.println("비즈다오01 roomListSelectWithAccom biz_id -> "+ biz_id);
			return sqlSession.selectList("roomListSelectWithAccom", biz_id);
		} catch (Exception e) {
			throw e;
		}	
		
	}

	@Override
	public List<Room_Img> selectAccomAllRoomImgList(String biz_id) {
		try {
			//System.out.println("비즈다오01 selectAccomAllRoomImgList biz_id -> "+ biz_id);
			return sqlSession.selectList("selectAccomAllRoomImgList", biz_id);
		} catch (Exception e) {
			throw e;
		}	
	}

	
}
