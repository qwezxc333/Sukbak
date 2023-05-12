
package com.example.S20230403.service.lysService;

import java.util.List;


import org.springframework.stereotype.Service;

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
public class DefaultServiceImple implements DefaultService { 
	private final Dao06 	d06;


	@Override
	public List<Accom> searchAc(Accom accom) { 
		List<Accom> searchAc
		=d06.searchAc(accom); System.out.println("서비스 임플 시작");

		return searchAc; 
	}

}
