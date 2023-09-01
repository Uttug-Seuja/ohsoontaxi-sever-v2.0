package ohsoontaxi.backend.domain.notification.domain.repository;

import ohsoontaxi.backend.domain.notification.domain.NotificationReservation;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationReservationRepository extends JpaRepository<NotificationReservation, Long> {

    Optional<NotificationReservation> findByReservation(Reservation reservation);

    List<NotificationReservation> findBySendAt(LocalDateTime now);

    @Modifying
    @Query("delete from NotificationReservation nr where nr.id in :notificationReservationIds")
    void deleteByIdIn(@Param("notificationReservationIds") List<Long> notificationReservationIds);
}
