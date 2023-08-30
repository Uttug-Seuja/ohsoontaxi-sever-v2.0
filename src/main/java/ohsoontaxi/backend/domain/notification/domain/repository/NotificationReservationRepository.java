package ohsoontaxi.backend.domain.notification.domain.repository;

import ohsoontaxi.backend.domain.notification.domain.NotificationReservation;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationReservationRepository extends JpaRepository<NotificationReservation, Long> {

    Optional<NotificationReservation> findByReservation(Reservation reservation);

    List<NotificationReservation> findBySendAt(LocalDateTime now);
}
