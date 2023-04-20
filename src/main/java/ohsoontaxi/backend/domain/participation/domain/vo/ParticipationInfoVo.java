package ohsoontaxi.backend.domain.participation.domain.vo;

import lombok.Builder;
import lombok.Getter;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.common.participation.SeatPosition;

@Getter
@Builder
public class ParticipationInfoVo {

    private final Long participationId;
    private final SeatPosition participationSeatPosition;
    private final UserInfoVO userInfoVO;
}
