package ohsoontaxi.backend.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationReservationScheduler {

    private final NotificationReservationUtils notificationReservationUtils;

    @Scheduled(cron = "0 0/1 * * * *")
    public void reservationNotification() {
        notificationReservationUtils.processScheduledReservation();
    }
}
