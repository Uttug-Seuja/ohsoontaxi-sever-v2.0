package ohsoontaxi.backend.domain.reservation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.common.user.Gender;


@Getter
public class HostInfoDto {

    private Long userId;
    private String name;
    private String schoolNum;
    private Gender gender;
    private String email;
    private String profilePath;

    public HostInfoDto(UserInfoVO userInfoVO) {

        userId = userInfoVO.getUserId();
        name = userInfoVO.getName();
        schoolNum = userInfoVO.getSchoolNum();
        gender = userInfoVO.getGender();
        email = userInfoVO.getEmail();
        profilePath = userInfoVO.getProfilePath();
    }
}
