package ohsoontaxi.backend.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import ohsoontaxi.backend.domain.notification.domain.vo.NotificationInfoVo;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.database.BaseEntity;

import java.time.LocalDateTime;
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

    private LocalDateTime reservedAt;

    @JoinColumn(name = "reservation_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

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
            Reservation reservation) {
        Notification notification =
                Notification.builder()
                        .title(title)
                        .content(content)
                        .reservation(reservation)
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
            LocalDateTime reservedAt,
            Reservation reservation) {
        return Notification.builder()
                .title(title)
                .content(content)
                .reservedAt(reservedAt)
                .reservation(reservation)
                .build();
    }

    public void addReceiver(DeviceToken deviceToken) {
        this.receivers.add(new NotificationReceiver(
                this,
                User.of(deviceToken.getUserId()),
                deviceToken.getToken()));
    }

}
