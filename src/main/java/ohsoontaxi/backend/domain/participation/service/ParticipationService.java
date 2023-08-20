package ohsoontaxi.backend.domain.participation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.domain.repository.ParticipationRepository;
import ohsoontaxi.backend.domain.participation.exception.*;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.CreateParticipationRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.request.UpdateSeatPositionRequest;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationListResponse;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.service.ReservationUtils;
import ohsoontaxi.backend.domain.temperature.service.TemperatureUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.participation.SeatPosition;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        currentReservation.addCurrentNum();
        currentReservation.changeReservationStatus();

        currentUser.getTemperature().addParticipationNum();

        temperatureUtils.temperaturePatch(currentUser.getId());
    }

    @Transactional
    public void updateSeatPosition(Long reservationId, UpdateSeatPositionRequest updateSeatPositionRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();
        Reservation currentReservation = reservationUtils.queryReservation(reservationId);

        Participation participation = participationRepository.findByUserAndReservation(currentUser, currentReservation);

        validDeadLine(currentReservation);
        participation.validUserIsHost(currentUser.getId());
        validDuplicatedSeatPosition(currentReservation, updateSeatPositionRequest.getSeatPosition());

        participation.updateSeatPosition(updateSeatPositionRequest.getSeatPosition());
    }

    @Transactional
    public void deleteParticipation(Long reservationId) {
        User currentUser = userUtils.getUserFromSecurityContext();
        Reservation currentReservation = reservationUtils.queryReservation(reservationId);

        Participation currentParticipation = participationRepository.findByUserAndReservation(currentUser, currentReservation);

        validDeadLine(currentParticipation.getReservation());
        currentParticipation.validUserIsHost(currentUser.getId());
        validIsHost(currentUser, currentParticipation.getReservation());

        currentUser.getTemperature().subParticipationNum();

        participationRepository.delete(currentParticipation);

        currentReservation.subtractCurrentNum();
        currentReservation.changeReservationStatus();

        temperatureUtils.temperaturePatch(currentUser.getId());
    }

    public ParticipationListResponse getParticipationList(Long reservationId) {
        User currentUser = userUtils.getUserFromSecurityContext();
        Reservation currentReservation = reservationUtils.queryReservation(reservationId);
        List<Participation> participationList = participationRepository.findAllByReservation(currentReservation);

        boolean result = participationRepository.existsByReservationAndUser(currentReservation, currentUser);

        return new ParticipationListResponse(result, participationList);
    }

    private void validIsHost(User user, Reservation reservation) {
        if (reservation.getUser() == user) {
            throw IsHostParticipation.EXCEPTION;
        }
    }
    private void validDuplicatedParticipation(User user, Reservation reservation) {
        boolean result = participationRepository.existsByUserAndReservation(user, reservation);
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
        validReservationStatus(reservation);
        validDeadLine(reservation);
        validDuplicatedParticipation(user, reservation);
        validDuplicatedSeatPosition(reservation, seatPosition);
    }


    @Override
    public Participation queryParticipation(Long id) {
        return participationRepository.findById(id).orElseThrow(() -> ParticipationNotFoundException.EXCEPTION);
    }

    @Override
    public Participation findParticipation(Long userId, Long reservationId) {
        User user = userUtils.getUserById(userId);
        Reservation reservation = reservationUtils.queryReservation(reservationId);
        return participationRepository.findByReservationAndUser(reservation, user).orElseThrow(() -> ParticipationNotFoundException.EXCEPTION);
    }
}