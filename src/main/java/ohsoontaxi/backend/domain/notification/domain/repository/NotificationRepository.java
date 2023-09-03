package ohsoontaxi.backend.domain.notification.domain.repository;

import ohsoontaxi.backend.domain.notification.domain.Notification;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long>, CustomNotificationRepository{

    Optional<Notification> findByReservation(Reservation reservation);

    Optional<Notification> findById(Long notificationId);

    @Modifying(flushAutomatically = true)
    @Query("update Notification n set n.reservation = null where n.reservation.id = :reservationId")
    void changeReservationNull(@Param("reservationId") Long reservationId);
}
