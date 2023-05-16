package ohsoontaxi.backend.domain.participation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.CreateParticipationRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.UpdateSeatPositionRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationListResponse;
import ohsoontaxi.backend.domain.participation.service.ParticipationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/create/{reservationId}")
    public void createParticipation(
            @PathVariable("reservationId") Long reservationId,
            @Valid @RequestBody CreateParticipationRequest createParticipationRequest) {
        participationService.createParticipation(reservationId, createParticipationRequest);
    }

    @PatchMapping("/update/{reservationId}")
    public void updateParticipation(
            @PathVariable("reservationId") Long reservationId,
            @Valid @RequestBody UpdateSeatPositionRequest updateSeatPositionRequest) {
        participationService.updateSeatPosition(reservationId, updateSeatPositionRequest);
    }

    @DeleteMapping("/delete/{reservationId}")
    public void deleteParticipation(@PathVariable("reservationId") Long reservationId) {
        participationService.deleteParticipation(reservationId);
    }

    @GetMapping("/getParticipationList/{reservationId}")
    public ParticipationListResponse getParticipationList(@PathVariable("reservationId") Long reservationId) {
        return participationService.getParticipationList(reservationId);
    }
}
