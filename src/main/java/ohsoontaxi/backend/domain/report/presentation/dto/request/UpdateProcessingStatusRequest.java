package ohsoontaxi.backend.domain.report.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.common.report.ProcessingStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateProcessingStatusRequest {
    
    private final ProcessingStatus processingStatus;
}
