package ohsoontaxi.backend.domain.participation.domain.repository;


import ohsoontaxi.backend.domain.participation.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
