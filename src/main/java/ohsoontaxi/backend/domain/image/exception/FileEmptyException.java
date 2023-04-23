package ohsoontaxi.backend.domain.image.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class FileEmptyException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new FileEmptyException();

    private FileEmptyException() {
        super(ErrorCode.FILE_EMPTY);
    }
}
