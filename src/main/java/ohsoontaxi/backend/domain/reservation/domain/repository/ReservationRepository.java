package ohsoontaxi.backend.domain.reservation.domain.repository;


import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
