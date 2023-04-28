package ohsoontaxi.backend.domain.report.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.service.ParticipationUtils;
import ohsoontaxi.backend.domain.report.domain.Report;
import ohsoontaxi.backend.domain.report.domain.repository.ReportRepository;
import ohsoontaxi.backend.domain.report.exception.ReportNotFoundException;
import ohsoontaxi.backend.domain.report.presentation.dto.request.CreateReportRequest;
import ohsoontaxi.backend.domain.report.presentation.dto.request.UpdateProcessingStatusRequest;
import ohsoontaxi.backend.domain.report.presentation.dto.response.ReportResponse;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.temperature.service.TemperatureUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.report.ProcessingStatus;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService implements ReportUtils{

    private final ReportRepository reportRepository;
    private final ParticipationUtils participationUtils;
    private final UserUtils userUtils;
    private final TemperatureUtils temperatureUtils;

    @Transactional
    public ReportResponse createReport(Long participationId, CreateReportRequest createReportRequest) {
        Participation participation = participationUtils.queryParticipation(participationId);
        User currentUser = userUtils.getUserFromSecurityContext();

        Report report = Report.createReport(currentUser, participation, createReportRequest.getReportReason(), createReportRequest.getReportType());

        reportRepository.save(report);

        return  ReportResponse.from(report);
    }

    @Transactional
    public void updateProcessingStatus(Long reportId, UpdateProcessingStatusRequest request) {
        Report report = queryReport(reportId);
        ProcessingStatus processingStatus = request.getProcessingStatus();

        if (processingStatus == ProcessingStatus.HANDLING) {
            Temperature temperature = report.getParticipation().getUser().getTemperature();
            temperature.addReportNum();
            temperature.subParticipationNum();
            temperatureUtils.temperaturePatch(report.getParticipation().getUser().getId());
        } else if (processingStatus == ProcessingStatus.RETURN) {
            Temperature temperature = report.getParticipation().getUser().getTemperature();
            temperature.subReportNum();
            temperatureUtils.temperaturePatch(report.getParticipation().getUser().getId());
        }

        report.updateProcessingStatus(processingStatus);
    }

    public ReportResponse getReport(Long reportId) {
        Report report = queryReport(reportId);

        return ReportResponse.from(report);
    }



    @Override
    public Report queryReport(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }
}
