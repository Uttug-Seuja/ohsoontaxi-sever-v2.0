package ohsoontaxi.backend.domain.credential.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthTokensResponse {

    private final String accessToken;
    private final String refreshToken;
}

