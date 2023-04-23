package ohsoontaxi.backend.domain.asset.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class ProfileImageNotFoundException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new ProfileImageNotFoundException();

    private ProfileImageNotFoundException() {
        super(ErrorCode.PROFILE_IMAGE_NOT_FOUND);
    }
}