package ohsoontaxi.backend.domain.reservation.domain.repository;

import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ReservationRepositoryCustom {

    Slice<Reservation> keywordBySlice(String word, Pageable pageable);
    Slice<Reservation> searchBySlice(String word, Pageable pageable);


}
