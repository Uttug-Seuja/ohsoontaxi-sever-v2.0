package ohsoontaxi.backend.domain.reservation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.participation.domain.vo.ParticipationInfoVo;
import ohsoontaxi.backend.global.common.participation.SeatPosition;
import ohsoontaxi.backend.global.common.user.Gender;


@Getter
public class ParticipationInfoDto {

    private Long participationId;
    private SeatPosition participationSeatPosition;
    private Long userId;
    private String name;
    private String schoolNum;
    private Gender gender;

    public ParticipationInfoDto(ParticipationInfoVo participationInfoVo) {

        participationId = participationInfoVo.getParticipationId();
        participationSeatPosition = participationInfoVo.getParticipationSeatPosition();
        userId = participationInfoVo.getUserInfoVO().getUserId();
        name = participationInfoVo.getUserInfoVO().getName();
        schoolNum = participationInfoVo.getUserInfoVO().getSchoolNum();
        gender = participationInfoVo.getUserInfoVO().getGender();
    }
}
