package ohsoontaxi.backend.domain.credential.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthTokenInfoDto {
    private String idToken;
    private String accessToken;
}