
package com.example.S20230403.service.lysService;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.S20230403.dao.lysDao.AccomAllDao;

import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Payment; 
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Soldout;

import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j 
public class AccomAllControllerImpl implements accomAllController { 
	private final AccomAllDao 	d06;

	@Override 
	public List<Payment> getr_name(Payment pmt) { 
		List<Payment>
		getr_name =d06.getr_name(pmt); 
		return getr_name; 
	}
	//결제쪽
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
	    int successCount = 0; // Variable to store the number of successful operations
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

	        // All operations are successful
	        if (successCount != 3) { // If not all operations are successful
	            throw new RuntimeException("Failed to execute all operations");
	        }

	        return 1;
	    } catch (Exception e) {
	        // Error occurred, perform additional actions for error handling
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
			//System.out.println("getResvDate  오류: " + e.getMessage());
		}		
		return getResvDate;
	}
}
