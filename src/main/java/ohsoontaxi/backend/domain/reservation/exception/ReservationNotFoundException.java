package ohsoontaxi.backend.domain.reservation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class ReservationNotFoundException extends OhSoonException {
    public static final OhSoonException EXCEPTION = new ReservationNotFoundException();
    private ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }
}
