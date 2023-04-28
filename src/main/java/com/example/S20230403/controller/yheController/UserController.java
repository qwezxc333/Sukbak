package com.example.S20230403.controller.yheController;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.handler.yheHandler.MailHandler;
import com.example.S20230403.model.Users;
import com.example.S20230403.service.yheService.UsersService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UsersService userService;
    private final JavaMailSenderImpl javaMailSender;

    @GetMapping("/login")
    public String login() {
        return "views/login";
    }

    @PostMapping("/checkDuplication")
    @ResponseBody
    public int checkDuplication(@RequestParam String user_id) {
        return userService.exists(user_id);
    }

    @PostMapping("/checkDuplicationNick")
    @ResponseBody
    public int checkDuplicationNick(@RequestParam String nickname) {
        return userService.existsNick(nickname);
    }

    @GetMapping("/sign")
    public String signForm() {
        return "views/signForm";
    }

    @PostMapping("/sign")
    public String sign(@ModelAttribute Users user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        request.setAttribute("msg", "회원가입을 축하드립니다. 로그인을 해주세요.");
        request.setAttribute("nextPage", "/");
        request.getRequestDispatcher("/denied-page").forward(request, response);
        return null;
    }

    @GetMapping("/additional-info")
    public String showAdditionalInfo(@AuthenticationPrincipal PrincipalDetail userDetail, Model model) {
        String user_id = userDetail.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetail.getAuthorities();

        model.addAttribute("user_id", user_id);
        model.addAttribute("authorities", authorities);
        return "additional-info";
    }

    @PostMapping("/updateInfo")
    public String submitAdditionalInfoForm(@ModelAttribute("user") Users user, Authentication authentication) {
        PrincipalDetail userDetails = (PrincipalDetail) authentication.getPrincipal();
        Users currentUser = userDetails.getUser();
        currentUser.setNickname(user.getNickname());
        currentUser.setPhone(user.getPhone());
        currentUser.setTelecom(user.getTelecom());
        currentUser.setGender(user.getGender());
        currentUser.setAuth_level(user.getAuth_level());
        userService.addInfoUser(currentUser);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("user_id") String user_id, HttpSession session) {
        userService.delete(user_id);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/seller")
    public @ResponseBody String seller() {
        return "seller";
    }

    @GetMapping("/reservation")
    @ResponseBody
    public String reservation1(@AuthenticationPrincipal PrincipalDetail userDetail) {
        return "권한 확인";
    }

    @GetMapping("/error")
    public String error() {
        return "redirect:/";
    }

    @PostMapping("/sendEmail")
    @ResponseBody
    public void sendEmail(HttpSession session, String user_id) {
        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);

            int randomNumber = (int) (Math.random() * 1000000);
            String certifiedNumber = String.format("%06d", randomNumber);

            mailHandler.setFrom("sukbak0401@gmail.com");
            mailHandler.setTo(user_id);
            mailHandler.setSubject("숙박 회원가입 인증메일입니다.");
            String content = "<p>인증번호 : " + certifiedNumber + "입니다.</p>";
            mailHandler.setText(content, true);

            mailHandler.send();

            session.setAttribute("certifiedNumber", certifiedNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/verify-email")
    @ResponseBody
    public ResponseEntity<String> verify_email(@RequestParam("certifiedNumber") String certifiedNumber,
                                               HttpSession session) {
        String savedCertifiedNumber = (String) session.getAttribute("certifiedNumber");

        if (savedCertifiedNumber != null && savedCertifiedNumber.equals(certifiedNumber)) {
            // 검증 성공
            return ResponseEntity.ok("인증에 성공했습니다.");
        } else {
            // 검증 실패
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }

}
