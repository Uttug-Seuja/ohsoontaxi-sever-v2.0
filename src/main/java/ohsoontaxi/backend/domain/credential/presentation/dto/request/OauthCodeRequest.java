package ohsoontaxi.backend.domain.credential.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthCodeRequest {
    private String code;
}