package ohsoontaxi.backend.domain.reservation.domain.repository;

import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ReservationRepositoryCustom {

    Slice<Reservation> keywordBySlice(String keyword, Pageable pageable);
    Slice<Reservation> searchBySlice(String keyword, Pageable pageable);



}
