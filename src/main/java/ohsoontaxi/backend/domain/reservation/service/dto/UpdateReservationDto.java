package ohsoontaxi.backend.domain.reservation.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateReservationDto {

    private String title;

    private String startPoint;

    private String destination;

    private LocalDateTime departureDate;

    private Double startLatitude;

    private Double startLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;
}
