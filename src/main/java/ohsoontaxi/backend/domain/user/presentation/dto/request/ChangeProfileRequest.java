package ohsoontaxi.backend.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeProfileRequest {

    @NotEmpty
    private String profilePath;
}
