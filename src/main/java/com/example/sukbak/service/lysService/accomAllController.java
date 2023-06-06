package com.example.sukbak.service.lysService;

import java.util.List;

import com.example.sukbak.model.Accom;
import com.example.sukbak.model.AccomPayment;
import com.example.sukbak.model.Notice_Faq;
import com.example.sukbak.model.Payment;
import com.example.sukbak.model.Room;
import com.example.sukbak.model.Soldout;
import com.example.sukbak.model.Users;

public interface accomAllController {

	List<Payment> getr_name(Payment pmt);

	List<AccomPayment> getapt(AccomPayment apt);

	List<Room> getR_price(AccomPayment apt);

	int paymentInsert(AccomPayment apt,List<String> resvDateList);

	List<Soldout> getResvDate(AccomPayment apt);

}
