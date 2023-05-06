package ohsoontaxi.backend.domain.user.domain.vo;

import lombok.Builder;
import lombok.Getter;
import ohsoontaxi.backend.domain.temperature.domain.vo.TemperatureInfoVo;
import ohsoontaxi.backend.global.common.user.Gender;

@Getter
@Builder
public class UserInfoVO {

    private final Long userId;
    private final String name;
    //private final String schoolNum;
    private final Gender gender;
    private final String email;
    private final String profilePath;
    private final TemperatureInfoVo temperatureInfoVo;
    private final String temperatureImage;
}