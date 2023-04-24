package ohsoontaxi.backend.domain.report.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateReportRequest {

    private final Long participationId;
    private final String reportReason;
}
