package com.example.S20230403.dao.yheDao;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.S20230403.model.Users;

@Repository
@RequiredArgsConstructor
public class UsersDaoImpl implements UsersDao{
    private final SqlSessionTemplate sqlSession;

    @Override
    public Users getUserAccount(String userId) {
        return sqlSession.selectOne("getUserAccount", userId);
    }

    @Override
    public void saveUser(Users user) {
        sqlSession.insert("saveUser",user);
    }

    @Override
    public int selectUserCountById(String user_id) {
        return sqlSession.selectOne("selectUserCountById",user_id);
    }
    @Override
    public int selectUserCountByNick(String nickname) {
        return sqlSession.selectOne("selectUserCountByNick",nickname);
    }
    @Override
    public int selectUserCountByPhone(String phone) {
        return sqlSession.selectOne("selectUserCountByPhone",phone);
    }

    @Override
    public void saveUser2Api(Users user) {
        sqlSession.insert("saveUser2Api", user);
    }

    @Override
    public int isNewUser(String user_id) {
        return sqlSession.selectOne("isNewUser",user_id);
    }

    @Override
    public void delete(String user_id) {
        sqlSession.delete("delete", user_id);
    }

    @Override
    public void addInfoUser(Users user) {
        sqlSession.update("addInfoUser", user);
    }

}
