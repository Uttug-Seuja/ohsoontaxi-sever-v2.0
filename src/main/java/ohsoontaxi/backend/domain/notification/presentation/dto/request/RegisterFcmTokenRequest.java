package ohsoontaxi.backend.domain.notification.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterFcmTokenRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String token;
}
