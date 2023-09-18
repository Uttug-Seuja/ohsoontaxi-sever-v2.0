package ohsoontaxi.backend.domain.email.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.email.presentation.dto.request.CodeRequestDto;
import ohsoontaxi.backend.domain.email.presentation.dto.request.EmailRequestDto;
import ohsoontaxi.backend.domain.email.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email 인증", description = "Email 인증 관련 API")
@RequestMapping("/api/v1/email")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @SecurityRequirements
    @Operation(summary = "학교 Email로 인증 코드 보내기")
    @PostMapping
    public void sendJoinMail(@Valid @RequestBody EmailRequestDto emailRequestDto) {
        emailService.sendMail(emailRequestDto);
    }

    @SecurityRequirements
    @Operation(summary = "인증 코드 맞는지 확인")
    @PostMapping("/code")
    public void checkCode(@Valid @RequestBody CodeRequestDto codeRequestDto) {
        emailService.checkCodeCorrect(codeRequestDto);
    }

}
