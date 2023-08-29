package ohsoontaxi.backend.domain.notification.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class NotificationInfoVo {

    private final Long notificationId;

    private final String title;

    private final String content;

    private final LocalDateTime createdDate;
}
