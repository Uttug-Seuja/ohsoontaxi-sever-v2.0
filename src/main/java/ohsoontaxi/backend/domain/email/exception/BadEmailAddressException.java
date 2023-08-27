package ohsoontaxi.backend.domain.email.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class BadEmailAddressException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new BadEmailAddressException();

    public BadEmailAddressException() {
        super(ErrorCode.BAD_EMAIL_ADDRESS);
    }
}
