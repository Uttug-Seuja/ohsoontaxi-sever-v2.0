package ohsoontaxi.backend.domain.reservation.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isEmpty;
import static ohsoontaxi.backend.domain.reservation.domain.QReservation.*;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Slice<Reservation> keywordBySlice(String word,Pageable pageable) {
        List<Reservation> reservations = queryFactory
                .selectDistinct(reservation)
                .from(reservation)
                .where(
                        titleContain(word)
                )
                .orderBy(reservation.title.length().asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(reservations, pageable, hasNext(reservations,pageable));
    }

    @Override
    public Slice<Reservation> searchBySlice(String word, Pageable pageable) {

        List<Reservation> reservations = queryFactory.selectFrom(reservation)
                .where(
                        // 기타 조건들
                        titleContain(word).
                                or(startContain(word)).
                                or(destinationContain(word))
                )
                .orderBy(reservation.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        // 무한 스크롤 처리
        return new SliceImpl<>(reservations, pageable, hasNext(reservations,pageable));
    }

    // 무한 스크롤 방식 처리하는 메서드
    private boolean hasNext(List<Reservation> results,Pageable pageable) {

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (results.size() > pageable.getPageSize()) {
            results.remove(pageable.getPageSize());
            hasNext = true;
        }

        return hasNext;
    }

    private BooleanExpression titleContain(String word) {
        return isEmpty(word) ? null : reservation.title.contains(word);
    }

    private BooleanExpression startContain(String word) {
        return isEmpty(word) ? null : reservation.startPoint.contains(word);
    }

    private BooleanExpression destinationContain(String word) {
        return isEmpty(word) ? null : reservation.destination.contains(word);
    }
}