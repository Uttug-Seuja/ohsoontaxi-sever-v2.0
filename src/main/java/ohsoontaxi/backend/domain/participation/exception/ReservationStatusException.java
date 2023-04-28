package ohsoontaxi.backend.domain.participation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class ReservationStatusException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new ReservationStatusException();
    private ReservationStatusException() {
        super(ErrorCode.FULL_PARTICIPATION_EXCEPTION);
    }
}
