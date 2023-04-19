package ohsoontaxi.backend.domain.report.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontaxi.backend.global.common.report.ProcessingStatus;

@Getter
@AllArgsConstructor
public class ReportInfoVo {

    private final Long reportId;
    private final String reportReason;
    private final ProcessingStatus processingStatus;
}
