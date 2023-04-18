package ohsoontaxi.backend.domain.participation.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.participation.exception.NotHostException;
import ohsoontaxi.backend.global.common.participation.SeatPosition;
import ohsoontaxi.backend.domain.participation.domain.vo.ParticipationInfoVo;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.user.domain.User;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Participation {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "participation_id")
    private Long id;

    @Enumerated(STRING)
    private SeatPosition seatPosition;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public Participation(User user, Reservation reservation, SeatPosition seatPosition) {
        this.user = user;
        this.reservation = reservation;
        reservation.getParticipations().add(this);
        this.seatPosition = seatPosition;
    }

    public static Participation createParticipation(User user, Reservation reservation, SeatPosition seatPosition) {
        return builder()
                .user(user)
                .reservation(reservation)
                .seatPosition(seatPosition)
                .build();
    }

    public ParticipationInfoVo getParticipationInfoVo() {
        return ParticipationInfoVo.builder()
                .participationId(id)
                .participationSeatPosition(seatPosition)
                .build();
    }

    public void validUserIsHost(Long id) {
        if (!checkUserIsHost(id)) {
            throw NotHostException.EXCEPTION;
        }
    }

    public boolean checkUserIsHost(Long id) {
        return user.getId().equals(id);
    }
    public void subParticipation(Reservation reservation) {
        reservation.getParticipations().remove(this);
    }
}
