package ohsoontaxi.backend.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeProfileRequest {

    @NotNull
    private String profilePath;
}
