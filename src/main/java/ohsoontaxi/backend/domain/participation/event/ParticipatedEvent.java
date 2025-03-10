package ohsoontaxi.backend.domain.participation.event;

import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.domain.notification.event.NotificationEvent;

import java.util.List;

public class ParticipatedEvent extends NotificationEvent {
    public ParticipatedEvent(List<DeviceToken> deviceTokens,
                             Long reservationId,
                             String titleMessage,
                             String contentMessage) {
        super(deviceTokens, reservationId, titleMessage, contentMessage);
    }
}
