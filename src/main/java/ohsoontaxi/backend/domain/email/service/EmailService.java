package ohsoontaxi.backend.domain.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.email.domain.EmailMessage;
import ohsoontaxi.backend.domain.email.domain.repository.EmailMessageRepository;
import ohsoontaxi.backend.domain.email.exception.*;
import ohsoontaxi.backend.domain.email.presentation.dto.request.CodeRequestDto;
import ohsoontaxi.backend.domain.email.presentation.dto.request.EmailRequestDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements EmailUtils{

    private final JavaMailSender javaMailSender;
    private final EmailMessageRepository emailMessageRepository;
    private final SpringTemplateEngine templateEngine;
    private static final String subject = "[오순택] 이메일 인증을 위한 인증 코드 발송";

    @Transactional
    public void sendMail(EmailRequestDto emailRequestDto) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        checkEmailAddress(emailRequestDto.getEmail());

        String code = createEmailMessage(emailRequestDto);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailRequestDto.getEmail());
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(setContext(code), true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw SendEmailFailException.EXCEPTION;
        }
    }

    @Transactional
    public void checkCodeCorrect(CodeRequestDto codeRequestDto){
        EmailMessage emailMessage = findEmailMessageOauthAndEmail(codeRequestDto.getOauthProvider(), codeRequestDto.getEmail());
        if(emailMessage.getIsApproved()){
            throw DuplicatedEmailException.EXCEPTION;
        }
        checkCodeValid(emailMessage.getLastModifyDate());
        if(!(emailMessage.getCode().equals(codeRequestDto.getCode()))) {
            throw CodeNotMatchedException.EXCEPTION;
        }
        emailMessage.changeStateTrue();
    }

    private String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email", context);
    }

    private void checkEmailAddress(String email){
        String ext = email.substring(email.lastIndexOf("@") + 1);
        if (!(ext.equals("sch.ac.kr"))) {
            throw BadEmailAddressException.EXCEPTION;
        }
    }

    private void checkCodeValid(LocalDateTime lastModifiedDate) {
        if (lastModifiedDate.plusMinutes(5).isBefore(LocalDateTime.now())){
            throw CodeExpiredException.EXCEPTION;
        }
    }

    @Override
    public EmailMessage findEmailMessageOauthAndEmail(String oauthProvider, String schEmail) {
        return emailMessageRepository.findByOauthProviderAndEmail(oauthProvider,schEmail)
                .orElseThrow(() -> EmailMessageNotFoundException.EXCEPTION);
    }

    private String createEmailMessage(EmailRequestDto emailRequestDto) {
        Optional<EmailMessage> optionalEmailMessage =
                emailMessageRepository.findByOauthProviderAndEmail(emailRequestDto.getOauthProvider(), emailRequestDto.getEmail());

        return optionalEmailMessage.map(emailMessage -> {
            if (emailMessage.getIsApproved()) {
                throw DuplicatedEmailException.EXCEPTION;
            } else {
                String code = createCode();
                emailMessage.changeCode(code);
                return code;
            }
        }).orElseGet(() -> {
            String code = createCode();
            emailMessageRepository.save(
                    EmailMessage.builder()
                            .email(emailRequestDto.getEmail())
                            .code(code)
                            .oauthProvider(emailRequestDto.getOauthProvider())
                            .isApproved(false)
                            .build());
            return code;
        });
    }

}