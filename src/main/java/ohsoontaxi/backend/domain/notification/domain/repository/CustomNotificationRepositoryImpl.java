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

    private <T> boolean hasNext(List<T> list, Pageable pageable) {
        boolean hasNext = false;
        if (list.size() > pageable.getPageSize()) {
            list.remove(pageable.getPageSize());
            hasNext = true;
        }
        return hasNext;
    }

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

    @Override
    public List<DeviceToken> findTokenByReservationId(Long reservationId) {
        return queryFactory
                .select(deviceToken)
                .from(deviceToken)
                .leftJoin(participation)
                .on(deviceToken.user.id.eq(participation.user.id))
                .where(
                        participation.reservation.id.eq(reservationId))
                .fetch();
    }

    @Override
    public Slice<Notification> findSliceByUserId(Long receiverId, Pageable pageable) {
        List<Notification> notifications = queryFactory
                .selectFrom(notification)
                .join(notification.receivers, notificationReceiver)
                .fetchJoin()
                .where(notificationReceiver.receiver.id.eq(receiverId))
                .orderBy(notification.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(notifications, pageable, hasNext(notifications, pageable));
    }

}
