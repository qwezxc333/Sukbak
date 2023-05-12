package com.example.S20230403.auth;

import com.example.S20230403.dao.yheDao.UsersDao;
import com.example.S20230403.model.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UsersDao userDao;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String user_id = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = new BCryptPasswordEncoder().encode(uuid);

        Users user = userDao.getUserAccount(user_id);

        if (user == null) {
            user = new Users();
            user.setUser_id(user_id);
            user.setPassword(password);
            user.setName(name);
            user.setAuth_level("TEMPORARY");

            userDao.saveUser2Api(user);
        }

        return new PrincipalDetail(user, oAuth2User.getAttributes());
    }
}
