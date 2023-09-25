package ohsoontaxi.backend.domain.reservation.presentation.dto.request;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String startPoint;

    @NotBlank
    private String destination;

    @Future
    private LocalDateTime departureDate;

    private Gender gender;

    private Double startLatitude;

    private Double startLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;
}
