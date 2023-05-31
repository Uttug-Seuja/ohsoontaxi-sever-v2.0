package ohsoontaxi.backend.domain.reservation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;
import java.util.List;

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

    private LocalDateTime createDate;

    private LocalDateTime lastModifyDate;

    private HostInfoDto hostInfo;

    private Boolean iHost;



    public ReservationResponse(ReservationBaseInfoVo reservationBaseInfoVo, UserInfoVO userInfoVO, boolean iHost) {

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
        createDate = reservationBaseInfoVo.getCreateDate();
        lastModifyDate = reservationBaseInfoVo.getLastModifyDate();
        hostInfo = new HostInfoDto(userInfoVO);
        this.iHost = iHost;


    }
}
