package ohsoontaxi.backend.domain.report.presentation.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.report.domain.Report;

@Getter
@RequiredArgsConstructor
public class ReportResponse {

    private final Long id;
    private final Long participationId;
    private final String reportReason;

    public static ReportResponse from(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getParticipation().getId(),
                report.getReportReason());
    }
}
