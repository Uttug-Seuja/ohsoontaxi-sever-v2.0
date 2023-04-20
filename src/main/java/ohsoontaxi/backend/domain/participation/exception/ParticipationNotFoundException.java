package ohsoontaxi.backend.domain.participation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class ParticipationNotFoundException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new ParticipationNotFoundException();

    private ParticipationNotFoundException() {
        super(ErrorCode.PARTICIPATION_NOT_FOUND);
    }
}
