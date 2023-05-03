package com.example.S20230403.handler.yheHandler;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.service.yheService.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UsersService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        PrincipalDetail userDetail = (PrincipalDetail) authentication.getPrincipal();
        String userId = userDetail.getUser().getUser_id();
        if (userService.isNewUser(userId) == 1) {
            request.setAttribute("msg", "추가 회원정보를 입력해야 이용 가능합니다.");
            request.setAttribute("nextPage", "/additional-info");
            request.getRequestDispatcher("/redirect-page").forward(request, response);
        } else {
            request.setAttribute("msg", "로그인 되었습니다.");
            request.setAttribute("nextPage", "/");
            request.getRequestDispatcher("/redirect-page").forward(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
