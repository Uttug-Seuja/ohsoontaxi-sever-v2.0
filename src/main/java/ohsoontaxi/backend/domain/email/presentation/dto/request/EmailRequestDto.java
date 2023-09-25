package ohsoontaxi.backend.domain.email.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmailRequestDto {

    @NotBlank
    private String email;

    @NotNull
    private String oauthProvider;
}
