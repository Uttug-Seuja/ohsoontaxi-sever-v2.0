package ohsoontaxi.backend.domain.notification.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterFcmTokenRequest {

    @NotNull
    private String deviceId;

    @NotNull
    private String token;
}
