package ohsoontaxi.backend.global.init;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.domain.ProfileImage;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.common.user.Gender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component  // 스프링빈 등록
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct  //bean이 여러 번 초기화되는 걸 방지할 수 있다.
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor // 생성자 주입
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {

            ProfileImage image1 = ProfileImage.createProfileImage(
                    "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C098f3311-eae2-4f51-8ae7-90b4fa0887fc.jpeg");
            ProfileImage image2 = ProfileImage.createProfileImage(
                    "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cea98a33e-8cda-49e9-ae0a-5ec66f935cea.jpeg");
            ProfileImage image3 = ProfileImage.createProfileImage(
                    "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Ceedc049d-b84e-427b-abb2-003f454af16d.jpeg");
            ProfileImage image4 = ProfileImage.createProfileImage(
                    "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg");

            em.persist(image1);
            em.persist(image2);
            em.persist(image3);
            em.persist(image4);

            Temperature temper = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(12)
                    .participationNum(34).build();

            Temperature temper1 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(4).build();

            Temperature temper2 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(0)
                    .participationNum(9).build();

            Temperature temper3 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(4).build();

            Temperature temper4 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(9).build();

            Temperature temper5 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(9).build();

            Temperature temper6 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(9).build();

            Temperature temper7 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(9).build();

            Temperature temper8 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(9).build();

            Temperature temper9 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(9).build();

            em.persist(temper);
            em.persist(temper1);
            em.persist(temper2);
            em.persist(temper3);
            em.persist(temper4);
            em.persist(temper5);
            em.persist(temper6);
            em.persist(temper7);
            em.persist(temper8);
            em.persist(temper9);

            User member1 = User.createUser("KAKAO","12312412423","이훈일",
                    "hunil12978@gmail.com","ajsk", Gender.MAN, temper1);
            User member2 = User.createUser("KAKAO","1231123123","김찬우",
                    "hunil92348@gmail.com","ajsk", Gender.WOMAN, temper);
            User member3 = User.createUser("KAKAO","12316346523","조준장",
                    "hunil9923@gmail.com","ajsk", Gender.MAN, temper2);
            User member4 = User.createUser("KAKAO","12316346523","이건희",
                    "hunil9923@gmail.com","ajsk", Gender.WOMAN, temper3);
            User member5 = User.createUser("KAKAO","12316346523","김세준",
                    "hunil9923@gmail.com","ajsk", Gender.MAN, temper4);
            User member6 = User.createUser("KAKAO","12316346523","김은지",
                    "hunil9923@gmail.com","ajsk", Gender.WOMAN, temper5);
            User member7 = User.createUser("KAKAO","12316346523","이은혜",
                    "hunil9923@gmail.com","ajsk", Gender.MAN, temper6);
            User member8 = User.createUser("KAKAO","12316346523","하재은",
                    "hunil9923@gmail.com","ajsk", Gender.WOMAN, temper7);
            User member9 = User.createUser("KAKAO","12316346523","김해나",
                    "hunil9923@gmail.com","ajsk", Gender.MAN, temper8);
            User member10 = User.createUser("KAKAO","12316346523","최유진",
                    "hunil9923@gmail.com","ajsk", Gender.WOMAN, temper9);



            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            em.persist(member6);
            em.persist(member7);
            em.persist(member8);
            em.persist(member9);
            em.persist(member10);



            makeReservation(member1,Gender.MAN,"A");

            makeReservation(member2,Gender.WOMAN,"B");

            makeReservation(member3,Gender.ALL,"C");

            em.flush();

            em.clear();


        }

        private void makeReservation(User member1,Gender gender, String etr) {
            for(int i =0; i<20; i++){

                Reservation reservation = Reservation.builder()
                        .user(member1)
                        .title("순대 갈사람"+etr)
                        .startPoint("신창역"+etr)
                        .destination("후문"+etr)
                        .departureDate(LocalDateTime.of(2024,1,12,11,30,1,3))
                        .reservationStatus(ReservationStatus.POSSIBLE)
                        .gender(gender)
                        .passengerNum(4)
                        .currentNum(0)
                        .startLatitude(123.1)
                        .startLongitude(123.1)
                        .destinationLatitude(123.1)
                        .destinationLongitude(123.1).build();

                em.persist(reservation);
            }
        }

    }
}
