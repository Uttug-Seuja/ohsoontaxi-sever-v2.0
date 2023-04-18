package ohsoontaxi.backend.domain.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.reservation.exception.NotHostException;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "reservation",cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<>();

    private String title;

    private String startPoint;

    private String destination;

    private LocalDateTime departureDate;

    private ReservationStatus reservationStatus;

    private Gender gender;

    private Integer passengerNum;

    private Integer currentNum;

    private Double startLatitude;

    private Double startLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;


    public void validUserIsHost(Long id) {
        if (!checkUserIsHost(id)) {
            throw NotHostException.EXCEPTION;
        }
    }

    public Boolean checkUserIsHost(Long id) {
        return user.getId().equals(id);
    }


}
