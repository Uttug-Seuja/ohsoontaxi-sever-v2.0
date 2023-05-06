package ohsoontaxi.backend.domain.participation.domain.repository;


import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.participation.SeatPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    boolean existsByReservationAndUser(Reservation reservation, User user);
    List<Participation> findAllByReservation(Reservation reservation);

    boolean existsByReservationAndSeatPosition(Reservation reservation, SeatPosition seatPosition);
    boolean existsByUserAndReservation(User user, Reservation reservation);
}
