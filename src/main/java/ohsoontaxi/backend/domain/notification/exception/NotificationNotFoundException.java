package ohsoontaxi.backend.domain.notification.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class NotificationNotFoundException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new NotificationNotFoundException();

    public NotificationNotFoundException() {
        super(ErrorCode.NOTIFICATION_NOT_FOUND);
    }
}
