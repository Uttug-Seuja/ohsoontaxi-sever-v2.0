package ohsoontaxi.backend.domain.notification.service;

import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.domain.user.domain.User;

import java.util.List;

public interface NotificationUtils {
    void sendNotification(List<DeviceToken> deviceTokens,
                          Long reservationId,
                          String titleMessage,
                          String contentMessage);

    List<DeviceToken> getDeviceTokens(User user, Long reservationId);
}
