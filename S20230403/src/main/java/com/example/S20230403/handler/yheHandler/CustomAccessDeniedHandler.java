package com.example.S20230403.handler.yheHandler;

import com.example.S20230403.auth.PrincipalDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        if (accessDeniedException != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null &&
                    ((PrincipalDetail) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("TEMPORARY"))) {
                request.setAttribute("msg", "추가 회원정보를 입력해야 이용 가능합니다.");
                request.setAttribute("nextPage", "/additional-info");

            } else if (authentication == null){
                request.setAttribute("msg", "로그인한 회원만 이용이 가능합니다.");
                request.setAttribute("nextPage", "/login");
                SecurityContextHolder.clearContext();
            }
        }
        request.setAttribute("msg", "허가되지 않은 접근입니다.");
        request.setAttribute("nextPage", "/");
        request.getRequestDispatcher("redirect-page").forward(request, response);
    }
}
