package ohsoontaxi.backend.domain.image.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class BadFileExtensionException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new BadFileExtensionException();

    private BadFileExtensionException() {
        super(ErrorCode.BAD_FILE_EXTENSION);
    }
}
