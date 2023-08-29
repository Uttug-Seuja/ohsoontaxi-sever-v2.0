package ohsoontaxi.backend.domain.notification.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class FcmTokenInvalidException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new FcmTokenInvalidException();

    public FcmTokenInvalidException() {
        super(ErrorCode.NOTIFICATION_FCM_TOKEN_INVALID);
    }
}
