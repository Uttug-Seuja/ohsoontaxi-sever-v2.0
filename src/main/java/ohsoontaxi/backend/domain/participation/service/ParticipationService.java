package ohsoontaxi.backend.domain.participation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.domain.repository.ParticipationRepository;
import ohsoontaxi.backend.domain.participation.exception.ParticipationNotFoundException;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.CreateParticipationRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationResponse;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.service.ReservationUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationService implements ParticipationUtils{

    private final ParticipationRepository participationRepository;
    private final UserUtils userUtils;
    private final ReservationUtils reservationUtils;

    public ParticipationResponse createParticipation(CreateParticipationRequest createParticipationRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();
        Reservation currentReservation = reservationUtils.queryReservation(createParticipationRequest.getReservationId());

        Participation participation = Participation.createParticipation(currentUser, currentReservation, createParticipationRequest.getSeatPosition());
        participationRepository.save(participation);

        return getParticipationResponse(participation);
    }

    public void deleteParticipation(Long participationId) {
        Participation currentParticipation = queryParticipation(participationId);

        currentParticipation.validUserIsHost(participationId);
        participationRepository.delete(currentParticipation);
    }

    private ParticipationResponse getParticipationResponse(Participation participation) {
        return new ParticipationResponse(participation.getParticipationInfoVo());
    }
    @Override
    public Participation queryParticipation(Long id) {
        return participationRepository.findById(id).orElseThrow(() -> ParticipationNotFoundException.EXCEPTION);
    }
}
