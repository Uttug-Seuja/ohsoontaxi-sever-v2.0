package ohsoontaxi.backend.domain.participation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.global.common.participation.SeatPosition;

@Getter
public class ParticipationInfoDto {

    private SeatPosition seatPosition;
    private ParticipationUserInfoDto userInfo;

    public ParticipationInfoDto(Participation participation) {
        seatPosition = participation.getSeatPosition();
        userInfo = new ParticipationUserInfoDto(participation.getUser().getUserInfo());
    }
}
