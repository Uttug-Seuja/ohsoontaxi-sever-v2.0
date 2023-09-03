package ohsoontaxi.backend.domain.notification.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.notification.domain.vo.NotificationInfoVo;

import java.time.LocalDateTime;

@Getter
public class QueryNotificationListResponseElement {

    private Long notificationId;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    public QueryNotificationListResponseElement(NotificationInfoVo notificationInfoVo) {
        notificationId = notificationInfoVo.getNotificationId();
        title = notificationInfoVo.getTitle();
        content = notificationInfoVo.getContent();
        createdDate = notificationInfoVo.getCreatedDate();
    }
}
