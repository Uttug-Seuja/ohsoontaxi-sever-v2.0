package ohsoontaxi.backend.domain.notification.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import ohsoontaxi.backend.domain.notification.presentation.dto.response.QueryNotificationListResponseElement;
import ohsoontaxi.backend.domain.notification.service.NotificationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

@Tag(name = "알림", description = "알림 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    //FCMToken 등록
    @Operation(summary = "FCM토큰 저장")
    @PostMapping("/token")
    public void registerFcmToken(@Valid @RequestBody RegisterFcmTokenRequest request) {
        notificationService.registerFcmToken(request);
    }

    //내가 받은 알림들 가져오기
    @Operation(summary = "내가 받은 알림들")
    @GetMapping
    @Parameters({
            @Parameter(name = "page", description = "Page number", example = "0", required = false),
            @Parameter(name = "size", description = "Page size", example = "10", required = false)
    })
    public Slice<QueryNotificationListResponseElement> queryListNotification(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size ) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return notificationService.queryListByUserId(pageRequest);
    }

    @Operation(summary = "알림 삭제")
    @DeleteMapping("/delete/{notificationId}")
    public void deleteNotification(@Parameter(description = "알림 Id", in = PATH)
            @PathVariable("notificationId") Long notificationId) {
        notificationService.deleteNotification(notificationId);
    }
}
