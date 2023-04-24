package ohsoontaxi.backend.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -868238202L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final EnumPath<AccountRole> accountRole = createEnum("accountRole", AccountRole.class);

    public final StringPath email = createString("email");

    public final EnumPath<ohsoontaxi.backend.global.common.user.Gender> gender = createEnum("gender", ohsoontaxi.backend.global.common.user.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath oauthId = createString("oauthId");

    public final StringPath oauthProvider = createString("oauthProvider");

    public final StringPath profilePath = createString("profilePath");

    public final StringPath schoolNum = createString("schoolNum");

    public final ohsoontaxi.backend.domain.temperature.domain.QTemperature temperature;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.temperature = inits.isInitialized("temperature") ? new ohsoontaxi.backend.domain.temperature.domain.QTemperature(forProperty("temperature")) : null;
    }

}

