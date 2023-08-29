package ohsoontaxi.backend.domain.notification.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.domain.notification.domain.repository.DeviceTokenRepository;
import ohsoontaxi.backend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import ohsoontaxi.backend.global.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NotificationService {

    private final DeviceTokenRepository deviceTokenRepository;
    private final EntityManager entityManager;

    @Transactional
    public void registerFcmToken(RegisterFcmTokenRequest request) {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        Optional<DeviceToken> deviceTokenOptional =
                deviceTokenRepository.findByDeviceId(request.getDeviceId());

        //deviceId로 deviceToken을 찾았을 때 있으면
        deviceTokenOptional.ifPresentOrElse(
                //deviceToken이 있으면
                deviceToken -> {
                    //deviceToken의 userId가 userId와 같으면
                    if (deviceToken.getUserId().equals(currentUserId)) {
                        //token 바꾸기
                        deviceToken.changeToken(request.getToken());
                    } else {
                        //해당 deviceToken 삭제
                        deviceTokenRepository.deleteById(deviceToken.getId());
                        entityManager.flush();
                        //새로 저장
                        deviceTokenRepository.save(
                                DeviceToken.of(
                                        currentUserId, request.getDeviceId(), request.getToken()));
                    }
                },
                //없으면 새로 저장
                () ->
                        deviceTokenRepository.save(
                                DeviceToken.of(
                                        currentUserId, request.getDeviceId(), request.getToken())));
    }
}
