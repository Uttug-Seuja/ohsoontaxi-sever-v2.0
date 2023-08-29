package ohsoontaxi.backend.domain.chat.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.HostInfoDto;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ChatResponse {

    private Long myParticipationId;

    private Long reservationId;

    private Integer passengerNum;

    private Integer currentNum;

    private HostInfoDto hostInfo;

    private Boolean iHost;

    private List<ChatHistoryDto> chatHistoryDtoList;

    public ChatResponse(Long ParticipationId,ReservationBaseInfoVo reservationBaseInfoVo, UserInfoVO userInfoVO, boolean iHost,List<ChatHistoryDto> ChatHistoryDtoList) {

        myParticipationId = ParticipationId;
        reservationId = reservationBaseInfoVo.getReservationId();
        passengerNum = reservationBaseInfoVo.getPassengerNum();
        currentNum = reservationBaseInfoVo.getCurrentNum();
        hostInfo = new HostInfoDto(userInfoVO);
        this.iHost = iHost;
        chatHistoryDtoList = ChatHistoryDtoList;

    }



//    private List<ChatPagingResponseDto> chatPagingResponseDtoList;
//

//
//    public ChatResponse(ReservationBaseInfoVo reservationBaseInfoVo, UserInfoVO userInfoVO, boolean iHost,List<ChatPagingResponseDto> chatPagingResponseDto) {
//
//        reservationId = reservationBaseInfoVo.getReservationId();
//        passengerNum = reservationBaseInfoVo.getPassengerNum();
//        currentNum = reservationBaseInfoVo.getCurrentNum();
//        hostInfo = new HostInfoDto(userInfoVO);
//        this.iHost = iHost;
//        chatPagingResponseDtoList = chatPagingResponseDto;
//
//    }

}
