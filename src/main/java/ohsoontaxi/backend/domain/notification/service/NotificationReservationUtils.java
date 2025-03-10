package ohsoontaxi.backend.domain.notification.service;

import java.time.LocalDateTime;

public interface NotificationReservationUtils {
    void recordNotificationReservation(Long reservationId, LocalDateTime departureDate, String content);
    void processScheduledReservation();
    void changeSendAtNotificationReservation(Long reservationId, LocalDateTime departureDate);
    void deleteNotificationReservation(Long reservationId);
}
