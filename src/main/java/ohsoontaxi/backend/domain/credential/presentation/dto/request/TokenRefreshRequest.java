package ohsoontaxi.backend.domain.credential.presentation.dto.request;

import lombok.Getter;

@Getter
public class TokenRefreshRequest {
    private String refreshToken;
}
