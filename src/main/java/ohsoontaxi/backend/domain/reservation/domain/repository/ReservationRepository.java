package ohsoontaxi.backend.domain.reservation.domain.repository;


import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Override
    Optional<Reservation> findById(Long id);
    Slice<Reservation> findSliceBy(Pageable pageable);

    Slice<Reservation> findAllByUserId(Pageable pageable,Long userId);
}
