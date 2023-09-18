package ohsoontaxi.backend.domain.email.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class DuplicatedEmailException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new DuplicatedEmailException();

    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATE_EMAILMESSAGE);
    }
}

