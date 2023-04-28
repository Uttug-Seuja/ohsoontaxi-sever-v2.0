package ohsoontaxi.backend.domain.report.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontaxi.backend.global.common.report.ReportType;

@Getter
@AllArgsConstructor
public class CreateReportRequest {

    private final Long participationId;
    private final String reportReason;
    private final ReportType reportType;
}
