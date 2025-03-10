package ohsoontaxi.backend.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import ohsoontaxi.backend.global.database.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate // 실제 값이 변경된 컬럼으로만 update 쿼리를 만드는 기능
@Entity
public class NotificationReservation extends BaseEntity {

    @Id
    @Column(name = "notification_reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime sendAt;

    private Long reservationId;

    public static NotificationReservation of(
            String title,
            String content,
            LocalDateTime sendAt,
            Long reservationId) {
        return NotificationReservation.builder()
                .title(title)
                .content(content)
                .sendAt(sendAt)
                .reservationId(reservationId)
                .build();
    }

    public void changeSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }
}
