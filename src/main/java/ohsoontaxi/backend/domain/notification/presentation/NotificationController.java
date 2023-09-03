package ohsoontaxi.backend.domain.notification.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import ohsoontaxi.backend.domain.notification.presentation.dto.response.QueryNotificationListResponseElement;
import ohsoontaxi.backend.domain.notification.service.NotificationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

    //내가 받은 알림들 가져오기
    @GetMapping
    public Slice<QueryNotificationListResponseElement> queryListNotification(
            @PageableDefault(size = 15, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return notificationService.queryListByUserId(pageable);
    }

    @DeleteMapping("/delete/{notificationId}")
    public void deleteNotification(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteNotification(notificationId);
    }
}
