package ohsoontaxi.backend.domain.report.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.report.domain.vo.ReportInfoVo;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.report.ProcessingStatus;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static ohsoontaxi.backend.global.common.report.ProcessingStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Report {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private String reportReason;

    @Enumerated(STRING)
    private ProcessingStatus processingStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "participation_id")
    private Participation participation;

    @Builder
    public Report(User user, Participation participation, String reportReason, ProcessingStatus processingStatus) {
        this.user = user;
        this.participation = participation;
        this.reportReason = reportReason;
        this.processingStatus = processingStatus;
    }

    public static Report createReport(User user, Participation participation, String reportReason) {
        return builder()
                .user(user)
                .participation(participation)
                .reportReason(reportReason)
                .processingStatus(RECEIPT)
                .build();
    }

    public ReportInfoVo getReportInfoVo() {
        return new ReportInfoVo(id, reportReason, processingStatus);
    }

    public void updateProcessingStatus(ProcessingStatus processingStatus) {
        this.processingStatus = processingStatus;
    }
}
