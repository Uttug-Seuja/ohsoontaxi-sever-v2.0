package ohsoontaxi.backend.domain.credential.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AfterOauthResponse {
    private String idToken;
    private String accessToken;
}