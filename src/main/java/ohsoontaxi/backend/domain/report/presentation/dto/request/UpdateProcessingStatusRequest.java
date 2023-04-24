package ohsoontaxi.backend.domain.report.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontaxi.backend.global.common.report.ProcessingStatus;

@Getter
@AllArgsConstructor
public class UpdateProcessingStatusRequest {

    private final ProcessingStatus processingStatus;
}
