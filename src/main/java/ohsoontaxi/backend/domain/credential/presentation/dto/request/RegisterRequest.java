package ohsoontaxi.backend.domain.credential.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.common.user.Gender;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private String profilePath;

    @NotBlank
    private String shcEmail;
}
