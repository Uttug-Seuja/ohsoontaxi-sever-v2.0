package ohsoontaxi.backend.domain.participation.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontaxi.backend.global.common.participation.SeatPosition;

@Getter
@AllArgsConstructor
public class CreateParticipationRequest {

    private Long reservationId;
    private SeatPosition seatPosition;
}
