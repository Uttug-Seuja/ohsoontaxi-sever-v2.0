package ohsoontaxi.backend.domain.report.exception;

import ohsoontaxi.backend.global.error.exception.ErrorCode;
import ohsoontaxi.backend.global.error.exception.OhSoonException;

public class ReportNotFoundException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new ReportNotFoundException();

    private ReportNotFoundException() {
        super(ErrorCode.REPORT_NOT_FOUND);
    }
}
