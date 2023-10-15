package ohsoontaxi.backend.global.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.notification.domain.repository.DeviceTokenRepository;
import ohsoontaxi.backend.domain.notification.event.DeviceTokenEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class DeviceTokenServiceEventHandler {

    private final DeviceTokenRepository deviceTokenRepository;

    @TransactionalEventListener(
            classes = DeviceTokenEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void deleteFcmToken(DeviceTokenEvent event) {
        deviceTokenRepository.deleteByUser(event.getUser());
    }
}