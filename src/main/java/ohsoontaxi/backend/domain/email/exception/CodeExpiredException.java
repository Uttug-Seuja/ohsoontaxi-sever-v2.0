package ohsoontaxi.backend.domain.email.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class CodeExpiredException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new CodeExpiredException();

    public CodeExpiredException() {
        super(ErrorCode.EXPIRED_CODE);
    }
}
