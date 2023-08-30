package ohsoontaxi.backend.domain.notification.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class NotificationReservationAlreadyExistException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new NotificationReservationAlreadyExistException();

    public NotificationReservationAlreadyExistException() {
        super(ErrorCode.NOTIFICATION_RESERVATION_ALREADY_EXIST);
    }
}
