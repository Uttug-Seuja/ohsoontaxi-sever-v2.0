package ohsoontaxi.backend.domain.participation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.participation.domain.vo.ParticipationInfoVo;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.participation.SeatPosition;

@Getter
public class ParticipationResponse {

    private Long participationId;
    private SeatPosition seatPosition;

    public ParticipationResponse(ParticipationInfoVo participationInfoVo) {
        participationId = participationInfoVo.getParticipationId();
        seatPosition = participationInfoVo.getParticipationSeatPosition();
    }
}
