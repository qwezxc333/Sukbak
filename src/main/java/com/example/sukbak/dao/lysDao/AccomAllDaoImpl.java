package com.example.sukbak.dao.lysDao;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.AccomPayment;
import com.example.sukbak.model.Notice_Faq;
import com.example.sukbak.model.Payment;
import com.example.sukbak.model.Qna;
import com.example.sukbak.model.Room;
import com.example.sukbak.model.Soldout;
import com.example.sukbak.model.Users;

public interface AccomAllDaoImpl {

	List<Accom> searchAc(Accom accom);

	List<Users> userlist(Users usid);

	int userNum(Users usid);

	int userTotal();

	int coundTotalUser(Users usid);

	List<Users> listSearchUsers(Users usid);

	List<Payment> getr_name(Payment pmt);

	List<AccomPayment> getapt(AccomPayment apt);

	List<Room> getR_price(AccomPayment apt);

	int insertPayment(AccomPayment apt);

	int insertReserv(AccomPayment apt);

	int insertSoldOut(List<String> resvDateList);
	

	List<Soldout> getResvDate(AccomPayment apt);


}
