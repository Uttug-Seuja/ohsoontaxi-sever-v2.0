package ohsoontaxi.backend.domain.participation.presentation.dto.response;

import lombok.Getter;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.common.user.Gender;

@Getter
public class ParticipationUserInfoDto {

    private Long userId;
    private String name;
    private String schoolNum;
    private Gender gender;
    private String email;
    private String profilePath;
    private Double temperature;
    private String temperatureImage;

    public ParticipationUserInfoDto(UserInfoVO userInfoVO) {
        userId = userInfoVO.getUserId();
        name = userInfoVO.getName();
        schoolNum = userInfoVO.getSchoolNum();
        gender = userInfoVO.getGender();
        email = userInfoVO.getEmail();
        profilePath = userInfoVO.getProfilePath();
        temperature = userInfoVO.getTemperatureInfoVo().getCurrentTemperature();
        temperatureImage = userInfoVO.getTemperatureImage();
    }
}
