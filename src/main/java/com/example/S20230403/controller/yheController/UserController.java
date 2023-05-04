package com.example.S20230403.controller.yheController;

import com.example.S20230403.auth.PrincipalDetail;
import com.example.S20230403.handler.yheHandler.MailHandler;
import com.example.S20230403.model.users.AddInfoCheck;
import com.example.S20230403.model.users.SaveCheck;
import com.example.S20230403.model.Users;
import com.example.S20230403.service.yheService.UsersService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

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

    @PostMapping("/checkDuplicationPhone")
    @ResponseBody
    public int checkDuplicationPhone(@RequestParam String phone) {
        return userService.existsPhone(phone);
    }
    @GetMapping("/sign")
    public String signForm(Model model) {
        model.addAttribute("users", new Users());
        return "views/signForm";
    }

    @PostMapping("/CheckPassword")
    @ResponseBody
    public boolean checkPassword(@RequestParam("password") String password, @RequestParam("checkPassword") String checkPassword) {
        return password.equals(checkPassword);
    }

    @PostMapping("/sign")
    public String sign(@Validated(SaveCheck.class) @ModelAttribute("users") Users user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "views/signForm";
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);

        model.addAttribute("nextPage", "/login");
        model.addAttribute("msg", "회원가입이 완료되었습니다.");
        return "/redirect-page";
    }

    @GetMapping("/additional-info")
    public String showAdditionalInfo(Model model) {
        model.addAttribute("users", new Users());
        return "additional-info";
    }

    @PostMapping("/updateInfo")
    public String submitAdditionalInfoForm(@Validated(AddInfoCheck.class) @ModelAttribute("users") Users user, BindingResult bindingResult,
                                           @AuthenticationPrincipal PrincipalDetail users, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "additional-info";
        }
        String user_id = users.getUsername();
        Users currentUser = new Users();
        currentUser.setUser_id(user_id);
        currentUser.setNickname(user.getNickname());
        currentUser.setPhone(user.getPhone());
        currentUser.setTelecom(user.getTelecom());
        currentUser.setGender(user.getGender());
        currentUser.setAuth_level(user.getAuth_level());
        userService.addInfoUser(currentUser);

        session.invalidate();

        model.addAttribute("nextPage", "/");
        model.addAttribute("msg", "추가 정보 입력이 완료되었습니다. 다시 로그인 해주세요.");
        return "redirect-page";
    }

    @PostMapping("/delete")
    public String deleteUser(@AuthenticationPrincipal PrincipalDetail users, HttpSession session, Model model) {
        String user_id = users.getUsername();
        userService.delete(user_id);
        session.invalidate();
        model.addAttribute("nextPage", "/");
        model.addAttribute("msg", "회원가입이 취소되었습니다.");
        return "/redirect-page";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
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
