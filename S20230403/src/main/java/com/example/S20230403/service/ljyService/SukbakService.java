package com.example.S20230403.service.ljyService;

import java.util.List;

import com.example.S20230403.model.Accom;
import com.example.S20230403.model.Comm;
import com.example.S20230403.model.Room;
import com.example.S20230403.model.Room_Img;
import com.example.S20230403.model.Users;

public interface SukbakService {

	void 			accomInsert(OwnerUser ownerUser);

	List<Accom>   	accomList(String id);

	List<Comm>		selectCommList(int i);

	Accom 			accomSelect(String biz_id);

	void 			accomUpdate(Accom accom);

	String 			getDBPassword(String user_id);

	Users 			getNowLoginUser(String user_id);

	int 			userDelete(String user_id);

	void 			userUpdate(Users nowLoginUser);

	List<Room> 		roomList(String user_id);

	int 			roomInsert(OwnerRoom ownerRoom);

	int 			getImgNum(Room room);

	void 			roomImgInsert(Room room);

	List<Room> 		selectRoomImgList(Room_Img ri);

	Room 			roomSelect(Room room);

	void 			roomImgDelete(Room room);

	void 			roomUpdate(OwnerRoom ownerRoom);

	void 			roomStatus(Room room, String string);

	boolean 		getBizId(String biz_id);

	void 			updateRoomCount(String biz_id, String string);

	void 			accomStatus(Accom accom, String string);

	List<Room> 		roomListSelectWithAccom(String biz_id);

	List<Room_Img>	selectAccomAllRoomImgList(String biz_id);
  
}     
            