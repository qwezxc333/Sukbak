package com.example.S20230403.service.lysService;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.AccomPayment;
import com.example.S20230403.model.Notice_Faq;
import com.example.S20230403.model.Payment;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Soldout;
import com.example.S20230403.model.Users;

public interface accomAllController {

	List<Payment> getr_name(Payment pmt);

	List<AccomPayment> getapt(AccomPayment apt);

	List<Room> getR_price(AccomPayment apt);

	int paymentInsert(AccomPayment apt,List<String> resvDateList);

	List<Soldout> getResvDate(AccomPayment apt);

}
