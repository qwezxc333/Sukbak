package com.example.S20230403.dao.lysDao;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Qna;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Soldout;
import com.example.S20230403.model.Users;

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
