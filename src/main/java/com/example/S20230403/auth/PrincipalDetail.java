package com.example.S20230403.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.S20230403.model.Users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class PrincipalDetail implements UserDetails, OAuth2User {
    private Users user;
    private Map<String, Object> attributes;

    //일반 로그인
    public PrincipalDetail(Users user) {
        this.user = user;
    }

    //OAuth 로그인
    public PrincipalDetail(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getAuth_level() != null) {
            switch (user.getAuth_level()) {
                case "USER":
                    authorities.add(new SimpleGrantedAuthority("USER"));
                    break;
                case "SELLER":
                    authorities.add(new SimpleGrantedAuthority("SELLER"));
                    break;
                case "ADMIN":
                    authorities.add(new SimpleGrantedAuthority("ADMIN"));
                    break;
                default:
                    authorities.add(new SimpleGrantedAuthority("TEMPORARY"));
            }
        } else {
            authorities.add(new SimpleGrantedAuthority("TEMPORARY"));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUser_id();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getUser_status().equals("activated");
    }


}
