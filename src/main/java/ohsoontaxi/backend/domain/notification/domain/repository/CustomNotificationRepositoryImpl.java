package ohsoontaxi.backend.domain.notification.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.notification.domain.DeviceToken;
import ohsoontaxi.backend.domain.notification.domain.Notification;
import ohsoontaxi.backend.domain.notification.domain.QDeviceToken;
import ohsoontaxi.backend.domain.notification.domain.QNotificationReceiver;
import ohsoontaxi.backend.domain.participation.domain.QParticipation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ohsoontaxi.backend.domain.notification.domain.QDeviceToken.deviceToken;
import static ohsoontaxi.backend.domain.notification.domain.QNotification.notification;
import static ohsoontaxi.backend.domain.notification.domain.QNotificationReceiver.*;
import static ohsoontaxi.backend.domain.participation.domain.QParticipation.participation;

@RequiredArgsConstructor
@Repository
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DeviceToken> findTokenByReservationIdNeUserId(Long reservationId, Long userId) {
        return queryFactory
                .select(deviceToken)
                .from(deviceToken)
                .leftJoin(participation)
                .on(deviceToken.user.id.eq(participation.user.id))
                .where(
                        participation.reservation.id.eq(reservationId),
                        deviceToken.user.id.ne(userId))
                .fetch();
    }

}
