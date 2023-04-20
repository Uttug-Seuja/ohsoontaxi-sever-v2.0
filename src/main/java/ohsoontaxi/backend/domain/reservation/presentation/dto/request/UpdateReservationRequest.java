package ohsoontaxi.backend.domain.reservation.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.reservation.service.dto.UpdateReservationDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReservationRequest {

    @NotNull
    @Size(min = 1, max = 18)
    private String title;

    public UpdateReservationDto toUpdateReservationDto() {
        return new UpdateReservationDto(title);
    }
}
