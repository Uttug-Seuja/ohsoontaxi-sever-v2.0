package ohsoontaxi.backend.domain.report.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.common.report.ReportType;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateReportRequest {

    private final String reportReason;
    private final ReportType reportType;
}
