package ohsoontaxi.backend.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.notification.domain.*;
import ohsoontaxi.backend.domain.notification.domain.repository.NotificationReservationRepository;
import ohsoontaxi.backend.domain.notification.exception.NotificationReservationAlreadyExistException;
import ohsoontaxi.backend.domain.notification.exception.NotificationReservationNotFoundException;
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
    public void recordNotificationReservation(Long reservationId,
                                              LocalDateTime departureDate,
                                              String content) {
        notificationReservationRepository.findByReservationId(reservationId)
                .ifPresent(notification -> {
                    throw NotificationReservationAlreadyExistException.EXCEPTION;
                });

        notificationReservationRepository.save(
                ohsoontaxi.backend.domain.notification.domain.NotificationReservation.of(
                        TitleMessage.TIME.getTitle(),
                        content,
                        departureDate.truncatedTo(ChronoUnit.MINUTES).minusMinutes(10),
                        reservationId));
    }

    @Transactional
    @Override
    public void processScheduledReservation() {
        List<ohsoontaxi.backend.domain.notification.domain.NotificationReservation> notificationReservations = retrieveReservation();
        if (notificationReservations.isEmpty()) {
            return;
        }

        deleteNotificationReservations(
                notificationReservations.stream().map(ohsoontaxi.backend.domain.notification.domain.NotificationReservation::getId).collect(Collectors.toList()));

        notificationReservations.forEach(
                notificationReservation ->
                        notificationUtils.sendNotification(
                                null,
                                notificationReservation.getReservationId(),
                                notificationReservation.getTitle(),
                                notificationReservation.getContent()));
    }

    private List<ohsoontaxi.backend.domain.notification.domain.NotificationReservation> retrieveReservation() {
        return notificationReservationRepository.findBySendAt(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    private void deleteNotificationReservations(List<Long> notificationReservationIds) {
        notificationReservationRepository.deleteByIdIn(notificationReservationIds);
    }

    @Transactional
    @Override
    public void changeSendAtNotificationReservation(Long reservationId, LocalDateTime departureDate) {
        ohsoontaxi.backend.domain.notification.domain.NotificationReservation notificationReservation = queryNotificationReservationByReservation(reservationId);
        notificationReservation.changeSendAt(departureDate);
    }

    @Transactional
    @Override
    public void deleteNotificationReservation(Long reservationId) {
        ohsoontaxi.backend.domain.notification.domain.NotificationReservation notificationReservation = queryNotificationReservationByReservation(reservationId);
        notificationReservationRepository.delete(notificationReservation);
    }

    private ohsoontaxi.backend.domain.notification.domain.NotificationReservation queryNotificationReservationByReservation(Long reservationId) {
        return notificationReservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> NotificationReservationNotFoundException.EXCEPTION);
    }
}
