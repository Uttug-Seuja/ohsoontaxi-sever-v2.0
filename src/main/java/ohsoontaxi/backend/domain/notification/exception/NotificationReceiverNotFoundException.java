package ohsoontaxi.backend.domain.notification.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class NotificationReceiverNotFoundException extends OhSoonException{

    public static final OhSoonException EXCEPTION = new NotificationReceiverNotFoundException();

    public NotificationReceiverNotFoundException() {
        super(ErrorCode.NOTIFICATION_RECEIVER_NOT_FOUND);
    }
}
