package ohsoontaxi.backend.domain.reservation.presentation.dto.response;


import lombok.Getter;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;

@Getter
public class ReservationBriefInfoDto {

    private Long reservationId;

    private String title;

    private String startPoint;

    private String destination;

    private LocalDateTime departureDate;

    private ReservationStatus reservationStatus;

    private Gender gender;

    private Integer passengerNum;

    private Integer currentNum;

    public ReservationBriefInfoDto(ReservationBaseInfoVo reservationBaseInfoVo) {
        reservationId = reservationBaseInfoVo.getReservationId();
        title = reservationBaseInfoVo.getTitle();
        startPoint = reservationBaseInfoVo.getStartPoint();
        destination = reservationBaseInfoVo.getDestination();
        departureDate = reservationBaseInfoVo.getDepartureDate();
        reservationStatus = reservationBaseInfoVo.getReservationStatus();
        gender = reservationBaseInfoVo.getGender();
        passengerNum = reservationBaseInfoVo.getPassengerNum();
        currentNum = reservationBaseInfoVo.getCurrentNum();
    }
}
