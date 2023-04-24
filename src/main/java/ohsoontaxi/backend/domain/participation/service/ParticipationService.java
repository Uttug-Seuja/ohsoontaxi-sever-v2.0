package ohsoontaxi.backend.domain.participation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.domain.repository.ParticipationRepository;
import ohsoontaxi.backend.domain.participation.exception.*;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.CreateParticipationRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.UpdateSeatPositionRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationResponse;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.service.ReservationUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.participation.SeatPosition;
import ohsoontaxi.backend.global.common.user.Gender;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationService implements ParticipationUtils{

    private final ParticipationRepository participationRepository;
    private final UserUtils userUtils;
    private final ReservationUtils reservationUtils;

    @Transactional
    public Long createParticipation(CreateParticipationRequest createParticipationRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();
        Reservation currentReservation = reservationUtils.queryReservation(createParticipationRequest.getReservationId());

        Participation participation = Participation.createParticipation(currentUser, currentReservation, createParticipationRequest.getSeatPosition());
        Long participationId = participationRepository.save(participation).getId();

        return participationId;
    }

    @Transactional
    public void updateSeatPosition(Long participationId, UpdateSeatPositionRequest updateSeatPositionRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();

        Participation participation = queryParticipation(participationId);

        participation.validUserIsHost(currentUser.getId());

        participation.updateSeatPosition(updateSeatPositionRequest.getSeatPosition());
    }

    @Transactional
    public void deleteParticipation(Long participationId) {
        User currentUser = userUtils.getUserFromSecurityContext();

        Participation currentParticipation = queryParticipation(participationId);

        currentParticipation.validUserIsHost(currentUser.getId());

        participationRepository.delete(currentParticipation);
    }

    public void getParticipationList(Long reservationId) {
        Reservation currentReservation = reservationUtils.queryReservation(reservationId);
        List<Participation> participationList = participationRepository.findAllByReservation(currentReservation);


    }

    public void validDuplicatedParticipation(Long participationId) {
        boolean result = participationRepository.existsById(participationId);
        if (result == true) {
            throw DuplicatedParticipationException.EXCEPTION;
        }
    }

    public void validEqualGender(Reservation reservation, User user) {
        Gender userGender = user.getGender();
        Gender reservationGender = reservation.getGender();
        if (reservationGender != Gender.ALL && userGender != reservationGender) {
            throw GenderException.EXCEPTION;
        }
    }

    public void validDuplicatedSeatPosition(Reservation reservation, SeatPosition seatPosition) {
        boolean result = participationRepository.existsByReservationAndSeatPosition(reservation, seatPosition);
        if (result == true) {
            throw DuplicatedSeatPositionException.EXCEPTION;
        }
    }

    public void validReservationStatus(Reservation reservation) {
        if (reservation.getCurrentNum().equals(4)) {
            throw ReservationStatusException.EXCEPTION;
        }
    }

    private ParticipationResponse getParticipationResponse(Participation participation) {
        return new ParticipationResponse(participation.getParticipationInfoVo());
    }

    @Override
    public Participation queryParticipation(Long id) {
        return participationRepository.findById(id).orElseThrow(() -> ParticipationNotFoundException.EXCEPTION);
    }
}
