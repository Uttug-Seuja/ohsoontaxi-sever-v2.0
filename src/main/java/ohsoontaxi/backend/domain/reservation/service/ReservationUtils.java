package ohsoontaxi.backend.domain.reservation.service;

import ohsoontaxi.backend.domain.reservation.domain.Reservation;

public interface ReservationUtils {

    Reservation queryReservation(Long id);
}
