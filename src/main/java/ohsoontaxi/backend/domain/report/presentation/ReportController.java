package ohsoontaxi.backend.domain.report.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.report.presentation.dto.request.CreateReportRequest;
import ohsoontaxi.backend.domain.report.presentation.dto.request.UpdateProcessingStatusRequest;
import ohsoontaxi.backend.domain.report.presentation.dto.response.ReportResponse;
import ohsoontaxi.backend.domain.report.service.ReportService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create/{userId}")
    public ReportResponse createReport(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody CreateReportRequest createReportRequest) {
        return reportService.createReport(userId, createReportRequest);
    }

    @PatchMapping("/process/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateProcessingStatus(
            @PathVariable("reportId") Long reportId,
            @Valid @RequestBody UpdateProcessingStatusRequest request) {
        reportService.updateProcessingStatus(reportId, request);
    }

    @GetMapping("/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReportResponse getReport(@PathVariable("reportId") Long reportId) {
        return reportService.getReport(reportId);
    }
}
