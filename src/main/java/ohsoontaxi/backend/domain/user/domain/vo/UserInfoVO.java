package ohsoontaxi.backend.domain.user.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontaxi.backend.global.common.user.Gender;

@Getter
@AllArgsConstructor
public class UserInfoVO {

    private final Long userId;
    private final String name;
    private final String schoolNum;
    private final Gender gender;
}