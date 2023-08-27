package ohsoontaxi.backend.domain.email.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class EmailMessageNotFoundException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new EmailMessageNotFoundException();

    public EmailMessageNotFoundException() {
        super(ErrorCode.EMAIL_MESSAGE_NOT_FOUND);
    }
}
