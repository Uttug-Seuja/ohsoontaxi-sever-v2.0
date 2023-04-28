package ohsoontaxi.backend.domain.participation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class ReservationDeadLineException extends OhSoonException{

    public static final OhSoonException EXCEPTION = new ReservationDeadLineException();
    private ReservationDeadLineException() {
        super(ErrorCode.RESERVATION_STATUS_EXCEPTION);
    }
}
