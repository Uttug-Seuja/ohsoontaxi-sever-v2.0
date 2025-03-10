package ohsoontaxi.backend.global.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.notification.event.NotificationEvent;
import ohsoontaxi.backend.domain.notification.service.NotificationUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventHandler {
    private final NotificationUtils notificationUtils;

    @TransactionalEventListener(
            classes = NotificationEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void sendNotification(NotificationEvent notificationEvent) {
        notificationUtils
                .sendNotification(
                        notificationEvent.getDeviceTokens(),
                        notificationEvent.getReservationId(),
                        notificationEvent.getTitleMessage(),
                        notificationEvent.getContentMessage());
    }
}
