package ohsoontaxi.backend.domain.image.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadImageResponse {

    @NotEmpty
    private final String imageUrl;
}
