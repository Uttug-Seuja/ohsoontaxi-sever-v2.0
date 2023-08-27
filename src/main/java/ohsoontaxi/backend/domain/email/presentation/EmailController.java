package ohsoontaxi.backend.domain.email.presentation;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.email.presentation.dto.request.CodeRequestDto;
import ohsoontaxi.backend.domain.email.presentation.dto.request.EmailRequestDto;
import ohsoontaxi.backend.domain.email.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/email")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    // 회원가입 이메일 인증
    @PostMapping
    public void sendJoinMail(@RequestBody EmailRequestDto emailRequestDto) {
        emailService.sendMail(emailRequestDto, "email");
    }

    //코드가 맞는지 확인
    @PostMapping("/code")
    public void checkCode(@RequestBody CodeRequestDto codeRequestDto) {
        emailService.checkCodeCorrect(codeRequestDto);
    }

}
