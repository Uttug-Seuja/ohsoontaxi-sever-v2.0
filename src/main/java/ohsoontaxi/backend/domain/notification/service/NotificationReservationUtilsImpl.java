package ohsoontaxi.backend.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.notification.domain.*;
import ohsoontaxi.backend.domain.notification.domain.repository.NotificationReservationRepository;
import ohsoontaxi.backend.domain.notification.exception.NotificationReservationAlreadyExistException;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationReservationUtilsImpl implements NotificationReservationUtils {

    private final NotificationReservationRepository notificationReservationRepository;
    private final NotificationUtils notificationUtils;

    @Transactional
    @Override
    public void recordNotificationReservation(Reservation reservation) {
        NotificationReservation notificationReservation =
                notificationReservationRepository.findByReservation(reservation)
                        .orElse(null);

        if(notificationReservation != null) {
            throw NotificationReservationAlreadyExistException.EXCEPTION;
        }

        notificationReservationRepository.save(
                NotificationReservation.of(
                        reservation.getDepartureDate().truncatedTo(ChronoUnit.MINUTES).minusMinutes(10),
                        reservation));
    }

    @Transactional
    @Override
    public void processScheduledReservation() {
        List<NotificationReservation> notificationReservations = retrieveReservation();

        if (notificationReservations.isEmpty()) {
            return;
        }

        deleteNotificationReservations(
                notificationReservations.stream().map(NotificationReservation::getId).collect(Collectors.toList()));

        notificationReservations.forEach(
                notificationReservation ->
                        notificationUtils.sendNotificationAll(
                                notificationReservation.getReservation(),
                                TitleMessage.TIME,
                                ContentMessage.TIME));
    }

    private List<NotificationReservation> retrieveReservation() {
        return notificationReservationRepository.findBySendAt(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    private void deleteNotificationReservations(List<Long> notificationReservationIds) {
        notificationReservationRepository.deleteByIdIn(notificationReservationIds);
    }
}
