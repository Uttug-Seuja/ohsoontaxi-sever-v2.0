package ohsoontaxi.backend.domain.participation.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class DuplicatedParticipationException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new DuplicatedParticipationException();
    private DuplicatedParticipationException() {
        super(ErrorCode.DUPLICATED_PARTICIPATION);
    }
}
