package ohsoontaxi.backend.domain.notification.domain.repository;

import ohsoontaxi.backend.domain.notification.domain.Notification;
import ohsoontaxi.backend.domain.notification.domain.NotificationReceiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationReceiverRepository extends JpaRepository<NotificationReceiver, Long> {
    Optional<NotificationReceiver> findNotificationReceiverByNotification(Notification notification);
}
