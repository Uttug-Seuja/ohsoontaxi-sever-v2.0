package ohsoontaxi.backend.domain.reservation.service;


import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.domain.repository.ReservationRepository;
import ohsoontaxi.backend.domain.reservation.presentation.dto.request.CreateReservationRequest;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ReservationResponse;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;



    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest createReservationRequest, User user){

        // TODO: 2023/04/19 로그인 구현 완료시 user 삭제
        //User user = userUtils.getUserFromSecurityContext();

        Reservation reservation = makeReservation(createReservationRequest, user);

        reservationRepository.save(reservation);

        return getReservationResponse(reservation);
    }

    private Reservation makeReservation(CreateReservationRequest createReservationRequest,User user){

        Reservation reservation = Reservation.builder()
                .user(user)
                .title(createReservationRequest.getTitle())
                .startPoint(createReservationRequest.getStartPoint())
                .destination(createReservationRequest.getDestination())
                .departureDate(createReservationRequest.getDepartureDate())
                .reservationStatus(ReservationStatus.POSSIBLE)
                .gender(createReservationRequest.getGender())
                .passengerNum(4)
                .currentNum(0)
                .startLatitude(createReservationRequest.getStartLatitude())
                .startLongitude(createReservationRequest.getStartLongitude())
                .destinationLatitude(createReservationRequest.getDestinationLatitude())
                .destinationLongitude(createReservationRequest.getDestinationLongitude())
                .build();

        return reservation;
    }

    // TODO: 2023/04/19 방주인 확인 로직 상황에 따라 추가
    private ReservationResponse getReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getReservationBaseInfoVo(),
                reservation.getParticipationInfoVOs());
    }

}
