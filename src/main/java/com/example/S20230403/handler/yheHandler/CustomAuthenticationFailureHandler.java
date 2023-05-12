package com.example.S20230403.handler.yheHandler;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private String defaultFailureUrl = "/login?error";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof DisabledException) {
            String msg = "탈퇴한 회원입니다. 다시 가입해주세요";
            String nextPage = "/";
            request.setAttribute("msg", msg);
            request.setAttribute("nextPage", nextPage);
            request.getRequestDispatcher("redirect-page").forward(request, response);
            return;
        }
        // 기타 실패 이유인 경우
        response.sendRedirect(request.getContextPath() + defaultFailureUrl);
    }
}

