package com.example.S20230403.dao.lysDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Soldout;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Repository
@RequiredArgsConstructor
@Slf4j
public class AccomAllDao implements AccomAllDaoImpl {
	private final SqlSession session;

	@Override
	public int userTotal() {
		int userTotal=0;
		try {
			userTotal =session.selectOne("ysuserTotal");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return userTotal;
	}

	@Override
	public List<Accom> searchAc(Accom accom) {
		List<Accom> searchAc =null;
		try {
			searchAc =session.selectList("yssearchAc",accom);
			//system.out.println("다오 임플 시작 서치쪽 "+searchAc.size());
		} catch (Exception e) {
			//system.out.println("다오 임플 서치쪽 에러 서치쪽 "+e.getMessage());
		}
		return searchAc;
	}

	@Override
	public List<Users> userlist(Users user_id) {
		List<Users> userlist =null;
		try {
			userlist =session.selectList("ysUserlist",user_id);
			//system.out.println("다오 임플 시작 일반 유저 사이즈 "+userlist.size());
		} catch (Exception e) {
			//system.out.println("userlist 쪽 에러 확잉용 다오 임플임 "+e.getMessage());
		}

		return userlist;
	}

	@Override
	public int userNum(Users user_id) {
		int userNum =0;
		try {
			userNum =session.selectOne("ysuserNum",user_id);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return userNum;
	}

	@Override
	public int coundTotalUser(Users user_id) {
		int condCount = 0;
		try {
			condCount = session.selectOne("condTotalUsers", user_id);
			//system.out.println("dao 카운팅 사이즈-> "+condCount);

		} catch (Exception e) {
			//system.out.println("dao coundTotalUser 에러 -> "+e.getMessage());
		}
		//system.out.println("dao condTotalEmp 끝");
		return condCount;
	}

	@Override
	public List<Users> listSearchUsers(Users users) {
		List<Users> listSearchUsers=null;
		try {
			//system.out.println("*DAo listSearchUsers users==*>"+users);
			// 키워드 검색
			listSearchUsers = session.selectList("ysSearchList",users);
			//system.out.println("dao listSearchUsers 사이즈-> "+listSearchUsers.size());
		} catch (Exception e) {
			//system.out.println("dao listSearchUsers 에러 -> "+e.getMessage());
		}

		return listSearchUsers;
	}


	@Override
	public List<Payment> getr_name(Payment pmt) {
		List<Payment> getr_name=null;
		try {
			getr_name=session.selectList("ysr_name",pmt);
			//system.out.println("*DAo getr_name 업소명==*>"+getr_name);

		} catch (Exception e) {
			//system.out.println("dao getr_name 에러 -> "+e.getMessage());
		}
		return getr_name;
	}

	@Override
	public List<AccomPayment> getapt(AccomPayment apt) {
		List<AccomPayment> getapt=null;
		try {
			getapt=session.selectList("ygetapt",apt);
			//system.out.println("*DAo getr_name 업소명==*>"+getapt);

		} catch (Exception e) {
			//system.out.println("dao getapt 에러 -> "+e.getMessage());
		}
		return getapt;		
	}

	@Override
	public List<Room> getR_price(AccomPayment apt) {
		List<Room> getR_price=null;
		try {
			getR_price=session.selectList("ygetR_pricet",apt);
			//system.out.println("*DAo getr_name 업소명==*>"+getR_price);

		} catch (Exception e) {
			//system.out.println("dao listSearchUsers 에러 -> "+e.getMessage());
		}
		return getR_price;		
	}


	// 결제 이루 한번에 적용될 insert 메서드들입니다.
	@Override
	public int insertPayment(AccomPayment apt) {
	    int result = 0;
	    try {
	        result = session.insert("ysInsertPayment", apt);
	        if (result > 0) {
	            //system.out.println("insertPayment 성공");
	            return 1;
	        } else {
	            //system.out.println("insertPayment 실패");
	            return 0;
	        }
	    } catch (Exception e) {
	        //system.out.println("insertPayment 오류: " + e.getMessage());
	        // 오류 처리를 위해 추가 작업 수행
	        e.printStackTrace();
	        return 0;
	    }
	}

	@Override
	public int insertReserv(AccomPayment apt) {
	    int result = 0;
	    try {
	        result = session.insert("ysInsertReserv", apt);
	        if (result > 0) {
	            //system.out.println("insertReserv 성공");
	            return 1;
	        } else {
	            //system.out.println("insertReserv 실패");
	            return 0;
	        }
	    } catch (Exception e) {
	        //system.out.println("insertReserv 오류: " + e.getMessage());
	        // 오류 처리를 위해 추가 작업 수행
	        e.printStackTrace();
	        return 0;
	    }
	}

	@Override
	public int insertSoldOut(List<String> resvDateList) {
	    int result = 0;
	    try {
	        result = session.insert("ysInsertSoldOut", resvDateList);
	        if (result > 0) {
	            //system.out.println("insertSoldOut 성공");
	            return 1;
	        } else {
	            //system.out.println("insertSoldOut 실패");
	            return 0;
	        }
	    } catch (Exception e) {
	        //system.out.println("insertSoldOut 오류: " + e.getMessage());
	        // 오류 처리를 위해 추가 작업 수행
	        e.printStackTrace();
	        return 0;
	    }
	}


	@Override
	public List<Soldout> getResvDate(AccomPayment apt) {
		List<Soldout> getResvDate=null;
		try {
			getResvDate=session.selectList("ysGetResvDatet",apt);
			//system.out.println("*DAo getResvDate 잘 가지고 왔냐??==*>"+getResvDate);

		} catch (Exception e) {
			//system.out.println("dao getResvDate 에러 -> "+e.getMessage());
		}
		return getResvDate;			
	}

}
