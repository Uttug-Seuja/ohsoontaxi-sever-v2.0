package ohsoontaxi.backend.domain.notification.service;

import ohsoontaxi.backend.domain.reservation.domain.Reservation;

public interface NotificationReservationUtils {

    void recordNotificationReservation(Reservation reservation);
    void processScheduledReservation();
    void changeSendAtNotificationReservation(Reservation reservation);
    void deleteNotificationReservation(Reservation reservation);
}
