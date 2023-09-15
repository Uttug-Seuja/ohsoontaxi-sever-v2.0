package ohsoontaxi.backend.domain.email.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class EmailRequestDto {

    @NotEmpty
    private String email;
}
