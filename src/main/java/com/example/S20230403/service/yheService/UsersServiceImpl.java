package com.example.S20230403.service.yheService;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.dao.yheDao.UsersDao;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService, UserDetailsService {
    private final UsersDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = userDao.getUserAccount(userId);

        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }
        return new PrincipalDetail(user);
    }

    @Override
    public Users getUserAccount(String userId) {
        return userDao.getUserAccount(userId);
    }

    @Override
    public void saveUser(Users user) {
        userDao.saveUser(user);
    }

    @Override
    public int exists(String user_id) {
        return userDao.selectUserCountById(user_id);
    }
    @Override
    public int existsNick(String nickname) {
        return userDao.selectUserCountByNick(nickname);
    }

    @Override
    public int existsPhone(String phone) {
        return userDao.selectUserCountByPhone(phone);
    }

    @Override
    public void saveUser2Api(Users user) {
        userDao.saveUser2Api(user);
    }

    @Override
    public int isNewUser(String userId) {
        return userDao.isNewUser(userId);
    }

    @Override
    public void delete(String userId) {
        userDao.delete(userId);
    }

    @Override
    public void addInfoUser(Users users) {
        userDao.addInfoUser(users);
    }
}
