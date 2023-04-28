package ohsoontaxi.backend.domain.participation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.domain.repository.ParticipationRepository;
import ohsoontaxi.backend.domain.participation.exception.*;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.CreateParticipationRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.UpdateSeatPositionRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationListResponse;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationResponse;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.service.ReservationUtils;
import ohsoontaxi.backend.domain.temperature.service.TemperatureUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.common.participation.SeatPosition;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationService implements ParticipationUtils{

    private final ParticipationRepository participationRepository;
    private final UserUtils userUtils;
    private final ReservationUtils reservationUtils;
    private final TemperatureUtils temperatureUtils;

    @Transactional
    public void createParticipation(Long reservationId, CreateParticipationRequest createParticipationRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();
        Reservation currentReservation = reservationUtils.queryReservation(reservationId);

        validMethod(currentReservation, currentUser, createParticipationRequest.getSeatPosition());

        Participation participation = Participation.createParticipation(currentUser, currentReservation, createParticipationRequest.getSeatPosition());
        participationRepository.save(participation);
        currentUser.getTemperature().addParticipationNum();
        temperatureUtils.temperaturePatch(currentUser.getId());
    }

    @Transactional
    public void updateSeatPosition(Long participationId, UpdateSeatPositionRequest updateSeatPositionRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();

        Participation participation = queryParticipation(participationId);

        participation.validUserIsHost(currentUser.getId());
        validDuplicatedSeatPosition(participation.getReservation(), updateSeatPositionRequest.getSeatPosition());

        participation.updateSeatPosition(updateSeatPositionRequest.getSeatPosition());
    }

    @Transactional
    public void deleteParticipation(Long participationId) {
        User currentUser = userUtils.getUserFromSecurityContext();

        Participation currentParticipation = queryParticipation(participationId);

        currentParticipation.validUserIsHost(currentUser.getId());

        currentUser.getTemperature().subParticipationNum();

        participationRepository.delete(currentParticipation);

        temperatureUtils.temperaturePatch(currentUser.getId());
    }

    public ParticipationListResponse getParticipationList(Long reservationId) {
        User currentUser = userUtils.getUserFromSecurityContext();
        Reservation currentReservation = reservationUtils.queryReservation(reservationId);
        List<Participation> participationList = participationRepository.findAllByReservation(currentReservation);

        boolean result = participationRepository.existsByReservationAndUser(currentReservation, currentUser);

        return new ParticipationListResponse(participationList, result);
    }

    private void validDuplicatedParticipation(Long participationId) {
        boolean result = participationRepository.existsById(participationId);
        if (result == true) {
            throw DuplicatedParticipationException.EXCEPTION;
        }
    }

    private void validEqualGender(Reservation reservation, User user) {
        Gender userGender = user.getGender();
        Gender reservationGender = reservation.getGender();
        if (reservationGender != Gender.ALL && userGender != reservationGender) {
            throw GenderException.EXCEPTION;
        }
    }

    private void validDuplicatedSeatPosition(Reservation reservation, SeatPosition seatPosition) {
        boolean result = participationRepository.existsByReservationAndSeatPosition(reservation, seatPosition);
        if (result == true) {
            throw DuplicatedSeatPositionException.EXCEPTION;
        }
    }

    private void validReservationStatus(Reservation reservation) {
        if (reservation.getCurrentNum().equals(4)) {
            throw ReservationStatusException.EXCEPTION;
        }
    }

    private void validDeadLine(Reservation reservation) {
        if (reservation.getReservationStatus().equals(ReservationStatus.DEADLINE)) {
            throw ReservationDeadLineException.EXCEPTION;
        }
    }

    private void validMethod(Reservation reservation, User user, SeatPosition seatPosition) {
        validEqualGender(reservation, user);
        validDeadLine(reservation);
        validReservationStatus(reservation);
        validDuplicatedParticipation(reservation.getId());
        validDuplicatedSeatPosition(reservation, seatPosition);
    }

    private ParticipationResponse getParticipationResponse(Participation participation) {
        return new ParticipationResponse(participation.getParticipationInfoVo());
    }

    @Override
    public Participation queryParticipation(Long id) {
        return participationRepository.findById(id).orElseThrow(() -> ParticipationNotFoundException.EXCEPTION);
    }
}
