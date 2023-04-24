package ohsoontaxi.backend.domain.participation.domain.repository;


import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.global.common.participation.SeatPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByReservation(Reservation reservation);

    boolean existsByReservationAndSeatPosition(Reservation reservation, SeatPosition seatPosition);
    @Override
    boolean existsById(Long participationId);
}
