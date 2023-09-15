package ohsoontaxi.backend.domain.credential.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TokenRefreshRequest {

    @NotNull
    private String refreshToken;
}
