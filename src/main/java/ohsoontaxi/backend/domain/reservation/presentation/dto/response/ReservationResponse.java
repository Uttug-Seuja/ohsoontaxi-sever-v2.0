package ohsoontaxi.backend.domain.reservation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.participation.domain.vo.ParticipationInfoVo;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReservationResponse {

    private Long reservationId;

    private String title;

    private String startPoint;

    private String destination;

    private LocalDateTime departureDate;

    private ReservationStatus reservationStatus;

    private Gender gender;

    private Integer passengerNum;

    private Integer currentNum;

    private Double startLatitude;

    private Double startLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;


    private List<ParticipationInfoDto> participaions;

    public ReservationResponse(ReservationBaseInfoVo reservationBaseInfoVo,  List<ParticipationInfoVo> participationInfoList) {

        reservationId = reservationBaseInfoVo.getReservationId();
        title = reservationBaseInfoVo.getTitle();
        startPoint = reservationBaseInfoVo.getStartPoint();
        destination = reservationBaseInfoVo.getDestination();
        departureDate = reservationBaseInfoVo.getDepartureDate();
        reservationStatus = reservationBaseInfoVo.getReservationStatus();
        gender = reservationBaseInfoVo.getGender();
        passengerNum = reservationBaseInfoVo.getPassengerNum();
        currentNum = reservationBaseInfoVo.getCurrentNum();
        startLatitude = reservationBaseInfoVo.getStartLatitude();
        startLongitude = reservationBaseInfoVo.getStartLongitude();
        destinationLatitude = reservationBaseInfoVo.getDestinationLatitude();
        destinationLongitude = reservationBaseInfoVo.getDestinationLongitude();

        participaions = participationInfoList.stream().map(ParticipationInfoDto::new).collect(Collectors.toList());

    }
}
