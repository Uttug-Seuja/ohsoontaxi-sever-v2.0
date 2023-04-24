package ohsoontaxi.backend.domain.report.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -1856906280L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ohsoontaxi.backend.domain.participation.domain.QParticipation participation;

    public final EnumPath<ohsoontaxi.backend.global.common.report.ProcessingStatus> processingStatus = createEnum("processingStatus", ohsoontaxi.backend.global.common.report.ProcessingStatus.class);

    public final StringPath reportReason = createString("reportReason");

    public final ohsoontaxi.backend.domain.user.domain.QUser user;

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.participation = inits.isInitialized("participation") ? new ohsoontaxi.backend.domain.participation.domain.QParticipation(forProperty("participation"), inits.get("participation")) : null;
        this.user = inits.isInitialized("user") ? new ohsoontaxi.backend.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

