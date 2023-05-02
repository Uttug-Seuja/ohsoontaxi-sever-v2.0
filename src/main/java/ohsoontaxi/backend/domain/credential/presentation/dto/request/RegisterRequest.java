package ohsoontaxi.backend.domain.credential.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.common.user.Gender;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private Gender gender;

    private String profilePath;
}
