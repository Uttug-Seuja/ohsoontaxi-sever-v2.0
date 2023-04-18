package ohsoontaxi.backend.domain.reservation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class NotHostException extends OhSoonException {
    public static final OhSoonException EXCEPTION = new NotHostException();
    private NotHostException() {
        super(ErrorCode.RESERVATION_NOT_HOST);
    }
}
