package ohsoontaxi.backend.domain.reservation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.reservation.presentation.dto.request.CreateReservationRequest;
import ohsoontaxi.backend.domain.reservation.presentation.dto.request.UpdateReservationRequest;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.KeywordDto;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ReservationBriefInfoDto;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ReservationResponse;
import ohsoontaxi.backend.domain.reservation.service.ReservationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ReservationResponse createReservation(
            @Valid @RequestBody CreateReservationRequest createReservationRequest) {
        return reservationService.createReservation(createReservationRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable("id") Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }
    @PatchMapping("/{id}")
    public ReservationResponse updateReservation(
            @PathVariable("id") Long reservationId,
            @Valid @RequestBody UpdateReservationRequest updateReservationRequest) {

        return reservationService.updateReservation(reservationId, updateReservationRequest);
    }

    // 방 상세조회
    @GetMapping("/{id}")
    public ReservationResponse getReservationDetail(@PathVariable("id") Long reservationId) {
        return reservationService.getReservationDetailById(reservationId);
    }

    @GetMapping("/list")
    public Slice<ReservationBriefInfoDto> getReservations(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"createdDate");
        return reservationService.findAllReservation(pageRequest);
    }

    @GetMapping("/my")
    public List<ReservationBriefInfoDto> getReservedByMe() {

        return reservationService.reservedByMe();
    }


    @GetMapping("/my/participation")
    public List<ReservationBriefInfoDto> getParticipated() {

        return reservationService.participatedReservation();
    }

    @GetMapping("/search/{keyword}")
    public Slice<ReservationBriefInfoDto> searchReservation(
            @PathVariable(value = "keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return reservationService.search(keyword,pageRequest);
    }

    @GetMapping("/search/keyword/{keyword}")
    public Slice<KeywordDto> searchKeyword(
            @PathVariable(value = "keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return reservationService.getKeyword(keyword,pageRequest);
    }

    @GetMapping("/search/recommend")
    public List<KeywordDto> recommendKeyword() {

        return reservationService.getRecommendWord();
    }


}
