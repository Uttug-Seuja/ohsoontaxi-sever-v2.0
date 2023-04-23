package ohsoontaxi.backend.domain.image.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class FileUploadFailException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new FileUploadFailException();

    private FileUploadFailException() {
        super(ErrorCode.FILE_UPLOAD_FAIL);
    }
}
