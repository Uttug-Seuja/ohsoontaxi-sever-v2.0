package ohsoontaxi.backend.domain.credential.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class RefreshTokenExpiredException extends OhSoonException {
    public static final OhSoonException EXCEPTION = new RefreshTokenExpiredException();

    private RefreshTokenExpiredException() {
        super(ErrorCode.REGISTER_EXPIRED_TOKEN);
    }
}
