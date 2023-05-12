package com.example.S20230403.dao.yheDao;

import com.example.S20230403.model.Users;

public interface UsersDao {
    Users getUserAccount(String userId);

    void saveUser(Users user);

    int selectUserCountById(String user_id);

    void saveUser2Api(Users user);

    int isNewUser(String userId);

    void delete(String userId);

    void addInfoUser(Users users);

    int selectUserCountByNick(String nickname);

    int selectUserCountByPhone(String phone);
}
