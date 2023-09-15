package ohsoontaxi.backend.domain.credential.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.common.user.Gender;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private Gender gender;

    @NotEmpty
    private String profilePath;
}
