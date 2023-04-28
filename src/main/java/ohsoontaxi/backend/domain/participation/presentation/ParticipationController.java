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

    @PatchMapping("/update/{participationId}")
    public void updateParticipation(
            @PathVariable("participationId") Long participation,
            @Valid @RequestBody UpdateSeatPositionRequest updateSeatPositionRequest) {
        participationService.updateSeatPosition(participation, updateSeatPositionRequest);
    }

    @DeleteMapping("/delete/{participationId}")
    public void deleteParticipation(@PathVariable("participationId") Long participationId) {
        participationService.deleteParticipation(participationId);
    }

    @GetMapping("/getParticipationList/{reservationId}")
    public ParticipationListResponse getParticipationList(@PathVariable("reservationId") Long reservationId) {
        return participationService.getParticipationList(reservationId);
    }
}
