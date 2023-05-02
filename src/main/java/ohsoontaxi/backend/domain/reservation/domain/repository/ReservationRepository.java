package ohsoontaxi.backend.domain.reservation.domain.repository;


import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>,ReservationRepositoryCustom {
    @Override
    Optional<Reservation> findById(Long id);
    Slice<Reservation> findSliceBy(Pageable pageable);

    List<Reservation> findTop5ByOrderByIdDesc();

    @Query("select distinct r from Reservation r"+
            " join fetch r.participations p"+
            " where p.user.id = :userId order by r.createdDate desc")
    List<Reservation> findParticipatedReservation(@Param("userId") Long userId);

    @Query("select distinct r from Reservation r"+
            " where r.user.id = :userId order by r.createdDate desc")
    List<Reservation> findReservedByMe(@Param("userId") Long userId);


}
