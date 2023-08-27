package ohsoontaxi.backend.domain.email.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class CodeNotMatchedException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new CodeNotMatchedException();

    public CodeNotMatchedException() {
        super(ErrorCode.CODE_NOT_MATCHED);
    }
}
