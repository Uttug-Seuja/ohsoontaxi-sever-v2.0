package ohsoontaxi.backend.domain.reservation.domain.vo;


import lombok.Builder;
import lombok.Getter;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationBaseInfoVo {

    private final Long reservationId;

    private final String title;

    private final String startPoint;

    private final String destination;

    private final LocalDateTime departureDate;

    private final ReservationStatus reservationStatus;

    private final Gender gender;

    private final Integer passengerNum;

    private final Integer currentNum;

    private final Double startLatitude;

    private final Double startLongitude;

    private final Double destinationLatitude;

    private final Double destinationLongitude;

}
