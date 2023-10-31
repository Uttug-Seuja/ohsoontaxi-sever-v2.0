package ohsoontaxi.backend.domain.reservation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class PastReservationDeleteException extends OhSoonException {
    public static final OhSoonException EXCEPTION = new PastReservationDeleteException();
    private PastReservationDeleteException() {
        super(ErrorCode.PAST_RESERVATION_EXCEPTION);
    }
}
