package ohsoontaxi.backend.domain.participation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipation is a Querydsl query type for Participation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipation extends EntityPathBase<Participation> {

    private static final long serialVersionUID = -1788617128L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipation participation = new QParticipation("participation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ohsoontaxi.backend.domain.reservation.domain.QReservation reservation;

    public final EnumPath<ohsoontaxi.backend.global.common.participation.SeatPosition> seatPosition = createEnum("seatPosition", ohsoontaxi.backend.global.common.participation.SeatPosition.class);

    public final ohsoontaxi.backend.domain.user.domain.QUser user;

    public QParticipation(String variable) {
        this(Participation.class, forVariable(variable), INITS);
    }

    public QParticipation(Path<? extends Participation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipation(PathMetadata metadata, PathInits inits) {
        this(Participation.class, metadata, inits);
    }

    public QParticipation(Class<? extends Participation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reservation = inits.isInitialized("reservation") ? new ohsoontaxi.backend.domain.reservation.domain.QReservation(forProperty("reservation"), inits.get("reservation")) : null;
        this.user = inits.isInitialized("user") ? new ohsoontaxi.backend.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

