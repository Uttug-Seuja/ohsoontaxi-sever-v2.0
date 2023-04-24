package ohsoontaxi.backend.domain.participation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;

import java.util.List;

@Getter
public class ParticipationListResponse {

    private List<UserInfoVO> users;
    private boolean iParticipation;

    public ParticipationListResponse(List<UserInfoVO> participationList) {

    }
}
