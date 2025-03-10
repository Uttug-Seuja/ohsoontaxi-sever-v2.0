package ohsoontaxi.backend.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import ohsoontaxi.backend.domain.notification.domain.vo.NotificationInfoVo;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.database.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long reservationId;

    @Builder.Default
    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NotificationReceiver> receivers = new ArrayList<>();

    private void addReceivers(List<DeviceToken> deviceTokens) {
        this.receivers.addAll(
                deviceTokens.stream()
                        .map(
                                deviceToken ->
                                        new NotificationReceiver(
                                                this,
                                                User.of(deviceToken.getUserId()),
                                                deviceToken.getToken()))
                        .collect(Collectors.toList()));
    }

    public static Notification makeNotificationWithReceivers(
            List<DeviceToken> deviceTokens,
            String title,
            String content,
            Long reservationId) {
        Notification notification =
                Notification.builder()
                        .title(title)
                        .content(content)
                        .reservationId(reservationId)
                        .build();
        notification.addReceivers(deviceTokens);
        return notification;
    }

    public NotificationInfoVo getNotificationInfoVo() {
        return NotificationInfoVo.builder()
                .notificationId(id)
                .title(title)
                .content(content)
                .createdDate(getCreatedDate())
                .build();
    }

    public static Notification of(
            String title,
            String content,
            Long reservationId) {
        return Notification.builder()
                .title(title)
                .content(content)
                .reservationId(reservationId)
                .build();
    }
}
