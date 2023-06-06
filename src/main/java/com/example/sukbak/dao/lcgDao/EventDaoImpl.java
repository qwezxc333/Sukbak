package com.example.sukbak.dao.lcgDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.sukbak.model.Event;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventDaoImpl implements EventDao {
	private final SqlSession session;

	@Override
	public List<Event> getEvent() {
		List<Event> sukbakEvents = null;
		try {
			sukbakEvents = session.selectList("getEvent");
		//	System.out.println("dao getevents 사이즈 -> "+sukbakEvents.size());
		} catch (Exception e) {
		//	System.out.println("dao getevents error -> "+e.getMessage());
			// TODO: handle exception
		}
		return sukbakEvents;
	}
}
