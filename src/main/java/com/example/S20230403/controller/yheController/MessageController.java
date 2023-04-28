package com.example.S20230403.controller.yheController;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class MessageController {
    final DefaultMessageService messageService;

    public MessageController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSYLSTTVDSQUHJG", "Z5YZ6ZB8SCWLCIWTEFTMGES3FKQDYAD5", "https://api.coolsms.co.kr");
    }

    @PostMapping("/send-one")
    public SingleMessageSentResponse sendOne(@RequestParam("phone") String phone, HttpSession session) {
        int randomNumber = (int) (Math.random() * 10000);
        String certifiedNumber = String.format("%04d", randomNumber); // 4자리 수로 변환

        // 세션에 인증번호 저장
        session.setAttribute("certifiedNumber", certifiedNumber);

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01055111960");
        message.setTo(phone);
        message.setText("인증번호는 " + certifiedNumber + " 입니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return response;
    }


    @PostMapping("/verify-certified-number")
    public ResponseEntity<String> verifyCertifiedNumber(@RequestParam("certifiedNumber") String certifiedNumber,
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
