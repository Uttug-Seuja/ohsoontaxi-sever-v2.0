package ohsoontaxi.backend.domain.reservation.presentation.dto.response;

import lombok.Getter;
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

    private Boolean iHost;


    //private List<ParticipationInfoDto> participations;  파라미터 List<ParticipationInfoVo> participationInfoList

    public ReservationResponse(ReservationBaseInfoVo reservationBaseInfoVo,boolean iHost) {

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
        this.iHost = iHost;
        //participations = participationInfoList.stream().map(ParticipationInfoDto::new).collect(Collectors.toList());

    }
}
