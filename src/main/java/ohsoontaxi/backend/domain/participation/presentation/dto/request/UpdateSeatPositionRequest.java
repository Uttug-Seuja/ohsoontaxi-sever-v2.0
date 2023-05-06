package ohsoontaxi.backend.domain.participation.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.common.participation.SeatPosition;

@Getter
@NoArgsConstructor
public class UpdateSeatPositionRequest {

    private SeatPosition seatPosition;
}
