package ohsoontaxi.backend.domain.report.presentation.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.report.domain.Report;
import ohsoontaxi.backend.global.common.report.ProcessingStatus;
import ohsoontaxi.backend.global.common.report.ReportType;

@Getter
@RequiredArgsConstructor
public class ReportResponse {

    private final Long id;
    private final Long participationUserId;
    private final String reportReason;
    private final ProcessingStatus processingStatus;
    private final ReportType reportType;

    public static ReportResponse from(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getParticipation().getId(),
                report.getReportReason(),
                report.getProcessingStatus(),
                report.getReportType());
    }
}
