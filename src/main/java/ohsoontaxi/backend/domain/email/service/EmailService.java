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
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements EmailUtils{

    private final JavaMailSender javaMailSender;
    private final EmailMessageRepository emailMessageRepository;
    private final SpringTemplateEngine templateEngine;
    private static final String subject = "[오순택] 이메일 인증을 위한 인증 코드 발송";

    //email 보내기
    @Transactional
    public void sendMail(EmailRequestDto emailRequestDto, String type) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        checkEmailAddress(emailRequestDto.getEmail());

        String authNum = createCode();

        EmailMessage emailMessage = createEmailMessage(emailRequestDto, authNum);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getEmail()); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw SendEmailFailException.EXCEPTION;
        }
    }

    //code 맞는지 확인
    @Transactional
    public void checkCodeCorrect(CodeRequestDto codeRequestDto){
        EmailMessage emailMessage = findEmailMessage(codeRequestDto.getEmail());
        checkCodeValid(emailMessage.getLastModifyDate());
        if (!(emailMessage.getCode().equals(codeRequestDto.getCode()))) {
            throw CodeNotMatchedException.EXCEPTION;
        }
        emailMessage.changeStateTrue();
    }

    // 인증번호 및 임시 비밀번호 생성 메서드
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

    // thymeleaf를 통한 html 적용
    private String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

    //email 메세지 저장하기
    private EmailMessage createEmailMessage(EmailRequestDto emailRequestDto, String code){
        Boolean bool = checkReceivedEmail(emailRequestDto.getEmail());

        if(bool){
            EmailMessage emailMessage = findEmailMessage(emailRequestDto.getEmail());
            emailMessage.changeCode(code);
            return emailMessage;
        } else{
            return emailMessageRepository.save(EmailMessage.builder()
                    .email(emailRequestDto.getEmail())
                    .code(code)
                    .oauthProvider(emailRequestDto.getOauthProvider())
                    .isApproved(false)
                    .build());
        }
    }

    //해당 email이 있는지 확인
    private Boolean checkReceivedEmail(String email){
        return emailMessageRepository.existsByEmail(email);
    }

    //email로 EmailMessage 찾기
    private EmailMessage findEmailMessage(String email){
        return emailMessageRepository.findByEmail(email).orElseThrow(() -> EmailMessageNotFoundException.EXCEPTION);
    }

    //넘어온 email이 sch형식이 맞는지 확인
    private void checkEmailAddress(String email){
        String ext = email.substring(email.lastIndexOf("@") + 1);
        if (!(ext.equals("sch.ac.kr"))) {
            throw BadEmailAddressException.EXCEPTION;
        }
    }

    //code 5분 넘었는지 확인
    private void checkCodeValid(LocalDateTime lastModifiedDate) {
        if (lastModifiedDate.plusMinutes(5).isBefore(LocalDateTime.now())){
            throw CodeExpiredException.EXCEPTION;
        }
    }

    //11시 55분 전에 만든 email은 삭제
    @Transactional
    public void deleteEmailMessages(){
        List<EmailMessage> emailMessages = retrieveEmailMessage();
        emailMessages.forEach(
                emailMessage ->
                        emailMessageRepository.delete(emailMessage));
    }

    //11시 55분전에 만든 emailMessage 가져오기
    private List<EmailMessage>  retrieveEmailMessage() {
        return emailMessageRepository.findByLastModifyDateLessThan(LocalDateTime.now().minusMinutes(5));
    }

    //oauthProvider와 schEmail로 EmailMessage찾기
    @Override
    public EmailMessage findEmailMessageOauthAndEmail(String oauthProvider, String schEmail) {
        return emailMessageRepository.findByOauthProviderAndEmail(oauthProvider,schEmail)
                .orElseThrow(() -> EmailMessageNotFoundException.EXCEPTION);
    }

}