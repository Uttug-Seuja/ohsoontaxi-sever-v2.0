package ohsoontaxi.backend.domain.notification.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.global.event.DomainEvent;

import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class NotificationEvent implements DomainEvent {
    private final List<DeviceToken> deviceTokens;
    private final Long reservationId;
    private final String titleMessage;
    private final String contentMessage;
}
