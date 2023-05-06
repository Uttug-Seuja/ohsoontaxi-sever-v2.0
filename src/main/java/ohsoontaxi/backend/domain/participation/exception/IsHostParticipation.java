package ohsoontaxi.backend.domain.participation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class IsHostParticipation extends OhSoonException {
    public static final OhSoonException EXCEPTION = new IsHostParticipation();
    private IsHostParticipation() {
        super(ErrorCode.IS_HOST_EXCEPTION);
    }
}
