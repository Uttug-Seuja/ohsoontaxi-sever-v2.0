package ohsoontaxi.backend.global.utils.notification;

import ohsoontaxi.backend.domain.notification.domain.ContentMessage;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.user.domain.User;

public class NotificationUtils {
    public static String makeContent(User user, ContentMessage contentMessage, Reservation reservation) {
        String content = "";
        if(user != null) {
            content += user.getName();
        }

        content += contentMessage.getContent1() +
                reservation.getTitle() +
                contentMessage.getContent2();
        return content;
    }
}
