package ohsoontaxi.backend.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.common.user.Gender;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private final Long id;
    private final String name;
    private final Gender gender;
    private final String email;
    private final String profilePath;
    private final Double currentTemperature;
    private final String temperatureImage;


    public UserProfileResponse(UserInfoVO userInfo) {
        this.id = userInfo.getUserId();
        this.name = userInfo.getName();
        this.gender = userInfo.getGender();
        this.email = userInfo.getEmail();
        this.profilePath = userInfo.getProfilePath();
        this.currentTemperature = userInfo.getTemperatureInfoVo().getCurrentTemperature();
        this.temperatureImage = userInfo.getTemperatureInfoVo().getTemperatureImage();
    }
}
