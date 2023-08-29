package ohsoontaxi.backend.domain.notification.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import ohsoontaxi.backend.domain.notification.service.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    //FCMToken 등록
    @PostMapping("/token")
    public void registerFcmToken(@Valid @RequestBody RegisterFcmTokenRequest request) {
        notificationService.registerFcmToken(request);
    }
}
