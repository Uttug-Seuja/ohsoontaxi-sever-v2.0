package ohsoontaxi.backend.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.database.BaseEntity;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NotificationReceiver extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "notification_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Notification notification;

    @JoinColumn(name = "receiver_user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User receiver;

    private String fcmToken;

    public NotificationReceiver(Notification notification, User receiver, String fcmToken) {
        this.notification = notification;
        this.receiver = receiver;
        this.fcmToken = fcmToken;
    }
}
