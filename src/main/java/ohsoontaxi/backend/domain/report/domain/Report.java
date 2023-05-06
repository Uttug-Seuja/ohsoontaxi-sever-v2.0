package ohsoontaxi.backend.domain.report.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.report.domain.vo.ReportInfoVo;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.report.ProcessingStatus;
import ohsoontaxi.backend.global.common.report.ReportType;
import ohsoontaxi.backend.global.database.BaseEntity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static ohsoontaxi.backend.global.common.report.ProcessingStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Report extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private String reportReason;

    @Enumerated(STRING)
    private ProcessingStatus processingStatus;

    @Enumerated(STRING)
    private ReportType reportType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "participation_id")
    private Participation participation;

    @Builder
    public Report(User user, Participation participation, String reportReason, ProcessingStatus processingStatus, ReportType reportType) {
        this.user = user;
        this.participation = participation;
        this.reportReason = reportReason;
        this.processingStatus = processingStatus;
        this.reportType = reportType;
    }

    public static Report createReport(User user, Participation participation, String reportReason, ReportType reportType) {
        return builder()
                .user(user)
                .participation(participation)
                .reportReason(reportReason)
                .processingStatus(RECEIPT)
                .reportType(reportType)
                .build();
    }

    public ReportInfoVo getReportInfoVo() {
        return new ReportInfoVo(id, reportReason, processingStatus);
    }

    public void updateProcessingStatus(ProcessingStatus processingStatus) {
        this.processingStatus = processingStatus;
    }
}
