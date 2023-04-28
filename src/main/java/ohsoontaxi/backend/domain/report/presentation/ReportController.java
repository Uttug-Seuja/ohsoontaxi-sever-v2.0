package ohsoontaxi.backend.domain.report.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.report.presentation.dto.request.CreateReportRequest;
import ohsoontaxi.backend.domain.report.presentation.dto.request.UpdateProcessingStatusRequest;
import ohsoontaxi.backend.domain.report.presentation.dto.response.ReportResponse;
import ohsoontaxi.backend.domain.report.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create/{participationId}")
    public ReportResponse createReport(
            @PathVariable("reservationId") Long reservationId,
            @Valid @RequestBody CreateReportRequest createReportRequest) {
        return reportService.createReport(reservationId, createReportRequest);
    }

    @PatchMapping("/process/{reportId}")
    public void updateProcessingStatus(
            @PathVariable("reportId") Long reportId,
            @Valid @RequestBody UpdateProcessingStatusRequest request) {
        reportService.updateProcessingStatus(reportId, request);
    }

    @GetMapping("/{reportId}")
    public ReportResponse getReport(@PathVariable("reportId") Long reportId) {
        return reportService.getReport(reportId);
    }
}
