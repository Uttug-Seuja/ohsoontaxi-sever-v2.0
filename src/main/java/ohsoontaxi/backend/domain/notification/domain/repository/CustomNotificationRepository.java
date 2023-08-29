package ohsoontaxi.backend.domain.notification.domain.repository;

import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.domain.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomNotificationRepository {

    List<DeviceToken> findTokenByReservationIdNeUserId(Long reservationId, Long userId);
    List<DeviceToken> findTokenByReservationId(Long reservationId);
}
