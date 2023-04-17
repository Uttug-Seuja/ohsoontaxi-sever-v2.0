package ohsoontaxi.backend.domain.report.domain.repository;


import ohsoontaxi.backend.domain.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
