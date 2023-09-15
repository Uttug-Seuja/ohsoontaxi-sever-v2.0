package ohsoontaxi.backend.domain.email.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class NotEmailApprovedException extends OhSoonException {
    public static final OhSoonException EXCEPTION = new NotEmailApprovedException();

    private NotEmailApprovedException() {
        super(ErrorCode.NOT_EMAIL_APPROVED);
    }

}

