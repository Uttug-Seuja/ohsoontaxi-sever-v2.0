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

    @Transactional
    public Long createReport(CreateReportRequest createReportRequest) {
        Participation participation = participationUtils.queryParticipation(createReportRequest.getParticipationId());
        User currentUser = userUtils.getUserFromSecurityContext();

        Report report = Report.createReport(currentUser, participation, createReportRequest.getReportReason());

        Long reportId = reportRepository.save(report).getId();

        return reportId;
    }

    public void updateProcessingStatus(Long reportId, UpdateProcessingStatusRequest request) {
        Report report = queryReport(reportId);
        ProcessingStatus processingStatus = request.getProcessingStatus();

        if (processingStatus == ProcessingStatus.HANDLING) {
            User reportedUser = report.getParticipation().getUser();
            Temperature temperature = reportedUser.getTemperature();
            temperature.addReportNum();
            temperature.subParticipationNum();
        }

        report.updateProcessingStatus(processingStatus);
    }

    @Override
    public Report queryReport(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }
}
