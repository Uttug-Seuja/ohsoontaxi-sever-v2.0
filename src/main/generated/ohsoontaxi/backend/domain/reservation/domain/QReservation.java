package ohsoontaxi.backend.domain.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -2109365448L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final ohsoontaxi.backend.global.database.QBaseEntity _super = new ohsoontaxi.backend.global.database.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> currentNum = createNumber("currentNum", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> departureDate = createDateTime("departureDate", java.time.LocalDateTime.class);

    public final StringPath destination = createString("destination");

    public final NumberPath<Double> destinationLatitude = createNumber("destinationLatitude", Double.class);

    public final NumberPath<Double> destinationLongitude = createNumber("destinationLongitude", Double.class);

    public final EnumPath<ohsoontaxi.backend.global.common.user.Gender> gender = createEnum("gender", ohsoontaxi.backend.global.common.user.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifyDate = _super.lastModifyDate;

    public final ListPath<ohsoontaxi.backend.domain.participation.domain.Participation, ohsoontaxi.backend.domain.participation.domain.QParticipation> participations = this.<ohsoontaxi.backend.domain.participation.domain.Participation, ohsoontaxi.backend.domain.participation.domain.QParticipation>createList("participations", ohsoontaxi.backend.domain.participation.domain.Participation.class, ohsoontaxi.backend.domain.participation.domain.QParticipation.class, PathInits.DIRECT2);

    public final NumberPath<Integer> passengerNum = createNumber("passengerNum", Integer.class);

    public final EnumPath<ohsoontaxi.backend.global.common.reservation.ReservationStatus> reservationStatus = createEnum("reservationStatus", ohsoontaxi.backend.global.common.reservation.ReservationStatus.class);

    public final NumberPath<Double> startLatitude = createNumber("startLatitude", Double.class);

    public final NumberPath<Double> startLongitude = createNumber("startLongitude", Double.class);

    public final StringPath startPoint = createString("startPoint");

    public final StringPath title = createString("title");

    public final ohsoontaxi.backend.domain.user.domain.QUser user;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new ohsoontaxi.backend.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

