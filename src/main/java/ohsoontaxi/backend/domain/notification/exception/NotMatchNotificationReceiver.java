package ohsoontaxi.backend.domain.notification.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class NotMatchNotificationReceiver extends OhSoonException {

    public static final OhSoonException EXCEPTION = new NotMatchNotificationReceiver();

    public NotMatchNotificationReceiver() {
        super(ErrorCode.NOT_MATCH_NOTIFICATION_RECEIVER);
    }
}
