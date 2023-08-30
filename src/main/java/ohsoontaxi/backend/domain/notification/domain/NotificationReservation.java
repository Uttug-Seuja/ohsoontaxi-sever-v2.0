package ohsoontaxi.backend.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
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

    private LocalDateTime sendAt;

    @JoinColumn(name = "reservation_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    public static NotificationReservation of(
            LocalDateTime sendAt,
            Reservation reservation) {
        return NotificationReservation.builder()
                .sendAt(sendAt)
                .reservation(reservation)
                .build();
    }
}
