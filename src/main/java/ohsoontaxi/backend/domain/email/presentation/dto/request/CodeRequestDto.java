package ohsoontaxi.backend.domain.email.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CodeRequestDto {

    @NotNull
    private String email;

    @NotBlank
    private String code;

    @NotNull
    private String oauthProvider;
}
