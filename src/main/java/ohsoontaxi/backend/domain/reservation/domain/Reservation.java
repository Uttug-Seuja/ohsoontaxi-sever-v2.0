package ohsoontaxi.backend.domain.reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.exception.GenderException;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;
import ohsoontaxi.backend.domain.reservation.exception.NotHostException;
import ohsoontaxi.backend.domain.reservation.service.dto.UpdateReservationDto;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;
import ohsoontaxi.backend.global.database.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer passengerNum;

    private Integer currentNum;

    private Double startLatitude;

    private Double startLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;

    @Builder
    public Reservation(User user,
                       String title,
                       String startPoint,
                       String destination,
                       LocalDateTime departureDate,
                       ReservationStatus reservationStatus,
                       Gender gender,
                       Integer passengerNum,
                       Integer currentNum,
                       Double startLatitude,
                       Double startLongitude,
                       Double destinationLatitude,
                       Double destinationLongitude) {
        this.user = user;
        this.title = title;
        this.startPoint = startPoint;
        this.destination = destination;
        this.departureDate = departureDate;
        this.reservationStatus = reservationStatus;
        this.gender = gender;
        this.passengerNum = passengerNum;
        this.currentNum = currentNum;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }

    public ReservationBaseInfoVo getReservationBaseInfoVo() {
        return ReservationBaseInfoVo.builder()
                .reservationId(id)
                .title(title)
                .startPoint(startPoint)
                .destination(destination)
                .departureDate(departureDate)
                .reservationStatus(reservationStatus)
                .gender(gender)
                .passengerNum(passengerNum)
                .currentNum(currentNum)
                .startLatitude(startLatitude)
                .startLongitude(startLongitude)
                .destinationLatitude(destinationLatitude)
                .destinationLongitude(destinationLongitude)
                .createDate(getCreatedDate())
                .lastModifyDate(getLastModifyDate())
                .hostInfoVO(user.getUserInfo())
                .build();
    }

    public void updateReservation(UpdateReservationDto updateGroupDto) {
        this.title = updateGroupDto.getTitle();

    }

    public void validUserIsHost(Long id) {
        if (!checkUserIsHost(id)) {
            throw NotHostException.EXCEPTION;
        }
    }

    public Boolean checkUserIsHost(Long id) {
        return user.getId().equals(id);
    }

    public void addCurrentNum(){this.currentNum++;}

    public void subtractCurrentNum(){this.currentNum--;}

    public void changeReservationStatus(){

        if(departureDate.isBefore(LocalDateTime.now())){
            reservationStatus = ReservationStatus.DEADLINE;
            return;
        }

        switch (currentNum){
            case 1, 2:
                reservationStatus = ReservationStatus.POSSIBLE;
                break;
            case 3:
                reservationStatus = ReservationStatus.IMMINENT;
                break;
            case 4:
                reservationStatus = ReservationStatus.DEADLINE;
        }

    }
    public void checkReservationGender(){

        if(!(user.getGender().equals(gender) || gender.equals(Gender.ALL))){
            throw GenderException.EXCEPTION;
        }

    }


}
