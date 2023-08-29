package ohsoontaxi.backend.domain.reservation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.chat.domain.Chat;
import ohsoontaxi.backend.domain.chat.presentation.dto.response.ChatPagingResponseDto;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationInfoDto;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChatRoomBriefInfoDto {

    private Long reservationId;

    private String title;

    private String startPoint;

    private String destination;

    private ReservationStatus reservationStatus;

    private LocalDateTime departureDate;

    private Gender gender;

    private Integer passengerNum;

    private Integer currentNum;

    private ChatPagingResponseDto latestChat;


//    public ChatRoomBriefInfoDto(ReservationBaseInfoVo reservationBaseInfoVo, List<Participation> participationList,ChatPagingResponseDto chatPagingResponseDto) {
//
//        reservationId = reservationBaseInfoVo.getReservationId();
//        title = reservationBaseInfoVo.getTitle();
//        startPoint = reservationBaseInfoVo.getStartPoint();
//        destination = reservationBaseInfoVo.getDestination();
//        departureDate = reservationBaseInfoVo.getDepartureDate();
//        gender = reservationBaseInfoVo.getGender();
//        currentNum = reservationBaseInfoVo.getCurrentNum();
//        latestChat = chatPagingResponseDto;
//        participationInfoList = participationList.stream().map(participation -> new ParticipationInfoDto(participation)).collect(Collectors.toList());
//
//    }

    public ChatRoomBriefInfoDto(ReservationBaseInfoVo reservationBaseInfoVo,ChatPagingResponseDto chatPagingResponseDto) {

        reservationId = reservationBaseInfoVo.getReservationId();
        title = reservationBaseInfoVo.getTitle();
        startPoint = reservationBaseInfoVo.getStartPoint();
        destination = reservationBaseInfoVo.getDestination();
        departureDate = reservationBaseInfoVo.getDepartureDate();
        reservationStatus = reservationBaseInfoVo.getReservationStatus();
        gender = reservationBaseInfoVo.getGender();
        passengerNum = reservationBaseInfoVo.getPassengerNum();
        currentNum = reservationBaseInfoVo.getCurrentNum();
        latestChat = chatPagingResponseDto;

    }
}
