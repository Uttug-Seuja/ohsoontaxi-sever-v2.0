package ohsoontaxi.backend.domain.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailScheduler {

    private final EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteEmailMessage() {
        emailService.deleteEmailMessages();
    }
}
