package ohsoontaxi.backend.domain.notification.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.notification.domain.ContentMessage;
import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.domain.notification.domain.Notification;
import ohsoontaxi.backend.domain.notification.domain.TitleMessage;
import ohsoontaxi.backend.domain.notification.domain.repository.DeviceTokenRepository;
import ohsoontaxi.backend.domain.notification.domain.repository.NotificationRepository;
import ohsoontaxi.backend.domain.notification.exception.FcmTokenInvalidException;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationUtilsImpl implements NotificationUtils{

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final DeviceTokenRepository deviceTokenRepository;

    @Override
    @Transactional
    public void changeReservationNull(Long reservationId) {
        notificationRepository.changeReservationNull(reservationId);
    }

    @Override
    @Transactional
    public void sendNotificationNoUser(User user, Reservation reservation, TitleMessage titleMessage,
                                       ContentMessage contentMessage) {
        List<DeviceToken> deviceTokens = notificationRepository.findTokenByReservationIdNeUserId(
                reservation.getId(), user.getId());
        for(DeviceToken deviceToken : deviceTokens) {
            log.info(deviceToken.getDeviceId());
        }
        List<String> tokens = getFcmTokens(deviceTokens);

        String title = getTitle(titleMessage);
        log.info(title);

        String content = user.getName() +
                contentMessage.getContent1() +
                reservation.getTitle() +
                contentMessage.getContent2();
        log.info(content);

        recordNotification(
                deviceTokens,
                title,
                content,
                reservation);

        if (tokens.isEmpty()) {
            return;
        }
        ApiFuture<BatchResponse> batchResponseApiFuture =
                fcmService.sendGroupMessageAsync(tokens, title, content);
        checkFcmResponse(deviceTokens, tokens, batchResponseApiFuture);
    }

    @Override
    @Transactional
    public void sendNotificationAll(Reservation reservation, TitleMessage titleMessage,
                                    ContentMessage contentMessage) {
        List<DeviceToken> deviceTokens = notificationRepository.findTokenByReservationId(
                reservation.getId());
        List<String> tokens = getFcmTokens(deviceTokens);
        log.info(tokens.get(0).toString());

        String title = getTitle(titleMessage);
        log.info(title);

        String content = contentMessage.getContent1() +
                reservation.getTitle() +
                contentMessage.getContent2();
        log.info(content);

        recordNotification(
                deviceTokens,
                title,
                content,
                reservation);

        if (tokens.isEmpty()) {
            return;
        }
        ApiFuture<BatchResponse> batchResponseApiFuture =
                fcmService.sendGroupMessageAsync(tokens, title, content);
        checkFcmResponse(deviceTokens, tokens, batchResponseApiFuture);
    }


    private List<String> getFcmTokens(List<DeviceToken> deviceTokens) {
        return deviceTokens.stream().map(DeviceToken::getToken).collect(Collectors.toList());
    }

    private String getTitle(TitleMessage titleMessage) {
        return titleMessage.getTitle();
    }

    private void recordNotification(
            List<DeviceToken> deviceTokens,
            String title,
            String content,
            Reservation reservation) {
        Notification notification =
                Notification.makeNotificationWithReceivers(
                        deviceTokens, title, content, reservation);
        notificationRepository.save(notification);
    }

    private void checkFcmResponse(
            List<DeviceToken> deviceTokens,
            List<String> tokens,
            ApiFuture<BatchResponse> batchResponseApiFuture) {
        try {
            List<SendResponse> responses = batchResponseApiFuture.get().getResponses();
            IntStream.iterate(0, i -> i + 1)
                    .limit(responses.size())
                    .filter(i -> responses.get(i).getException() != null)
                    .filter(
                            i ->
                                    responses
                                            .get(i)
                                            .getException()
                                            .getMessagingErrorCode()
                                            .equals(MessagingErrorCode.INVALID_ARGUMENT))
                    .forEach(
                            i -> {
                                String errorToken = tokens.get(i);
                                String errorMessage = responses.get(i).getException().getMessage();
                                MessagingErrorCode errorCode =
                                        responses.get(i).getException().getMessagingErrorCode();

                                Optional<DeviceToken> errorDeviceToken =
                                        deviceTokens.stream()
                                                .filter(
                                                        deviceToken ->
                                                                deviceToken
                                                                        .getToken()
                                                                        .equals(errorToken))
                                                .findAny();
                                Long IdOfErrorDeviceToken =
                                        errorDeviceToken.map(DeviceToken::getId).orElse(null);
                                Long userIdOfErrorDeviceToken =
                                        errorDeviceToken.map(DeviceToken::getUserId).orElse(null);
                                deviceTokenRepository.deleteById(IdOfErrorDeviceToken);

                                log.error(
                                        "**[sendGroupMessageAsync] errorUserId: {}, errorMessage: {}, errorCode: {}, errorToken: {}",
                                        userIdOfErrorDeviceToken,
                                        errorMessage,
                                        errorCode,
                                        errorToken);
                            });
        } catch (InterruptedException | ExecutionException e) {
            throw FcmTokenInvalidException.EXCEPTION;
        }
    }

}
