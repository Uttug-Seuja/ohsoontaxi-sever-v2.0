package ohsoontaxi.backend.domain.reservation.presentation.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.reservation.service.dto.UpdateReservationDto;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReservationRequest {

    @NotNull
    @Size(min = 1, max = 18)
    private String title;

    private String startPoint;

    private String destination;

    private LocalDateTime departureDate;

    private Double startLatitude;

    private Double startLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;

    public UpdateReservationDto toUpdateReservationDto() {
        return new UpdateReservationDto(
                title,
                startPoint,
                destination,
                departureDate,
                startLatitude,
                startLongitude,
                destinationLatitude,
                destinationLongitude);
    }
}
