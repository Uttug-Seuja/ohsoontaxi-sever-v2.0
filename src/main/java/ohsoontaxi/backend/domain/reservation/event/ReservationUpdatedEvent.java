package ohsoontaxi.backend.domain.reservation.event;

import lombok.Getter;
import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.domain.notification.event.NotificationEvent;

import java.util.List;

@Getter
public class ReservationUpdatedEvent extends NotificationEvent {
    public ReservationUpdatedEvent(List<DeviceToken> deviceTokens,
                                   Long reservationId,
                                   String titleMessage,
                                   String contentMessage) {
        super(deviceTokens, reservationId, titleMessage, contentMessage);
    }
}
