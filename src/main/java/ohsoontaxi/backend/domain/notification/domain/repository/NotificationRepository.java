package ohsoontaxi.backend.domain.notification.domain.repository;

import ohsoontaxi.backend.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long>, CustomNotificationRepository{
    Optional<Notification> findById(Long notificationId);
}
