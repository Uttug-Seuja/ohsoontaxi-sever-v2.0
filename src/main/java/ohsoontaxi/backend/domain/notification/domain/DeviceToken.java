package ohsoontaxi.backend.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.database.BaseEntity;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeviceToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(unique = true)
    private String deviceId;

    private String token;

    public Long getUserId() {
        return this.user.getId();
    }

    public static DeviceToken of(Long userId, String deviceId, String deviceToken) {
        return DeviceToken.builder()
                .user(User.of(userId))
                .deviceId(deviceId)
                .token(deviceToken)
                .build();
    }

    public void changeToken(String token) {
        this.token = token;
    }
}
