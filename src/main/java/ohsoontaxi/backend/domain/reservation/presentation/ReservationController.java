package ohsoontaxi.backend.domain.reservation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.reservation.presentation.dto.request.CreateReservationRequest;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ReservationResponse;
import ohsoontaxi.backend.domain.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // TODO: 2023/04/19 security 적용시 바꾸기
//    public ReservationResponse createReservation(
//            @Valid @RequestBody CreateReservationRequest createReservationRequest) {
//        return reservationService.createReservation(createReservationRequest);
//    }
}
