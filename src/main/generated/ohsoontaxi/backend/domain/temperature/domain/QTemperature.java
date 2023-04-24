package ohsoontaxi.backend.domain.temperature.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTemperature is a Querydsl query type for Temperature
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemperature extends EntityPathBase<Temperature> {

    private static final long serialVersionUID = -333401544L;

    public static final QTemperature temperature = new QTemperature("temperature");

    public final NumberPath<Double> currentTemperature = createNumber("currentTemperature", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> participationNum = createNumber("participationNum", Integer.class);

    public final NumberPath<Integer> reportedNum = createNumber("reportedNum", Integer.class);

    public QTemperature(String variable) {
        super(Temperature.class, forVariable(variable));
    }

    public QTemperature(Path<? extends Temperature> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTemperature(PathMetadata metadata) {
        super(Temperature.class, metadata);
    }

}

