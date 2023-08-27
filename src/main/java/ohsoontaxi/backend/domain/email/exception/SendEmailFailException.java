package ohsoontaxi.backend.domain.email.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class SendEmailFailException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new SendEmailFailException();

    public SendEmailFailException() {
        super(ErrorCode.EMAIL_SEND_FAIL);
    }
}
