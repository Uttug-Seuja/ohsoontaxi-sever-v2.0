package ohsoontaxi.backend.domain.notification.service;

import ohsoontaxi.backend.domain.notification.domain.ContentMessage;
import ohsoontaxi.backend.domain.notification.domain.TitleMessage;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.user.domain.User;

public interface NotificationUtils {
    void sendNotificationNoUser(
            User user,
            Reservation reservation,
            TitleMessage titleMessage,
            ContentMessage contentMessage);

    void sendNotificationAll(
            Reservation reservation,
            TitleMessage titleMessage,
            ContentMessage contentMessage);
}
