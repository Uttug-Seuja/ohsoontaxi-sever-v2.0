package ohsoontaxi.backend.domain.participation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class GenderException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new GenderException();
    private GenderException() {
        super(ErrorCode.GENDER_NOT_EQUAL);
    }
}
