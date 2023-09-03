package ohsoontaxi.backend.domain.notification.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class NotificationReservationNotFoundException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new NotificationReservationNotFoundException();


    public NotificationReservationNotFoundException() {
        super(ErrorCode.NOTIFICATION_RESERVATION_NOT_FOUND);
    }
}
