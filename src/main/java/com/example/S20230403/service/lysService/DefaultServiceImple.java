
package com.example.S20230403.service.lysService;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.S20230403.dao.lysDao.AccomAllDao;
import com.example.S20230403.model.Accom;
import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j 
public class DefaultServiceImple implements DefaultService { 
	private final AccomAllDao 	d06;
	@Override
	public List<Accom> searchAc(Accom accom) { 
		List<Accom> searchAc
		=d06.searchAc(accom); 

		return searchAc; 
	}

}
