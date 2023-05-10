
package com.example.S20230403.service.lysService;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.dao.lysDao.Dao06; import
com.example.S20230403.model.Accom; import
com.example.S20230403.model.AccomPayment; import
com.example.S20230403.model.Notice_Faq; import
com.example.S20230403.model.Payment; import com.example.S20230403.model.Room;
import com.example.S20230403.model.Soldout;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j 
public class ServiceImpl06 implements Service06 { 
	private final Dao06 	d06;



	@Override 
	public List<Users> userlist(Users users) { 
		List<Users> userlist
		=d06.userlist(users); System.out.println(" 서비스 임플에서 유저 찾기 시작!"); return
				userlist; 
	}

	@Override 
	public int userNum(Users users) { 
		int userNum =d06.userNum(users);
		return userNum; 
	}


	@Override 
	public int userTotal() { 
		int userTotal=d06.userTotal();

		return userTotal; 
	}

	@Override 
	public int conditionUserCount(Users users) {
		int condUserCnt
		=d06.coundTotalUser(users); System.out.println(" 서비스 임플에서 유저 찾기 시작!");

		return condUserCnt; 
	}

	@Override 
	public List<Users> listSearchUsers(Users users) {

		List<Users> listSearchUsers =d06.listSearchUsers(users);
		System.out.println(" 서비스 임플에서 유저 찾기 시작!");

		return listSearchUsers; 
	}


	@Override 
	public List<Payment> getr_name(Payment pmt) { 
		List<Payment>
		getr_name =d06.getr_name(pmt); 
		return getr_name; 
	}

	@Override 
	public List<AccomPayment> getapt(AccomPayment apt) {
		List<AccomPayment> getapt=d06.getapt(apt); 
		return getapt; 
	}

	@Override 
	public List<Room> getR_price(AccomPayment apt) { 
		List<Room>
		getR_price=d06.getR_price(apt); 
		return getR_price; 
	}


	@Override
	@Transactional
	public int paymentInsert(AccomPayment apt, List<String> resvDateList) {
		int successCount = 0; // 확인 성공 횟수를 저장하기 위한 변수
		try {
			

			// 예약 정보 저장
			if (d06.insertReserv(apt) == 0) {
				return 0;
			}
			successCount++;
			
			
			// 해당 일자의 숙소 예약 가능 수량 조정
			if (d06.insertPayment(apt) == 0) {

				return 0;
			}
			successCount++;

			// 결제 정보 저장
			if (d06.insertSoldOut(resvDateList) == 0) {

				return 0;
			}
			successCount++;

			System.out.println("paymentInsert 트랜잭션 성공");
			if (successCount == 3) { // 모두 성공할 경우
				return 1;
			}
			else { // 일부만 성공할 경우
				return 0;
			}
		} catch (Exception e) {
			System.out.println("paymentInsert 트랜잭션 오류: " + e.getMessage());
			System.out.println("successCount : " + successCount);
			// 오류 처리를 위해 추가 작업 수행
			e.printStackTrace();
			return 0;
		}
	}

	//체크인 체크 아웃 값을 이용해서 그 사이에 있는 기간을 리스트 형식으로 가지고 오는 작업
	@Override
	public List<Soldout> getResvDate(AccomPayment apt) {
		List<Soldout> getResvDate =null;
		try {
			getResvDate =d06.getResvDate(apt);

		} catch (Exception e) {
			System.out.println("getResvDate  오류: " + e.getMessage());
		}		
		return getResvDate;
	}
}
