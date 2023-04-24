package ohsoontaxi.backend.domain.participation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class DuplicatedSeatPositionException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new DuplicatedSeatPositionException();
    private DuplicatedSeatPositionException() {
        super(ErrorCode.DUPLICATED_SEATPOSITION);
    }
}
