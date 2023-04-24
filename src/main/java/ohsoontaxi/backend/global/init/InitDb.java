package ohsoontaxi.backend.global.init;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.user.Gender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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




            Temperature temper = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(12)
                    .participationNum(34).build();

            Temperature temper1 = Temperature.builder()
                    .currentTemperature(36.5)
                    .reportedNum(1)
                    .participationNum(4).build();

            em.persist(temper);
            em.persist(temper1);

            User member1 = User.createUser("KAKAO","12312412423","이훈일", "20181543",
                    "hunil12978@gmail.com","ajsk", Gender.MAN, temper1);
            User member2 = User.createUser("KAKAO","1231123123","김찬우", "20181666",
                    "hunil92348@gmail.com","ajsk", Gender.WOMAN, temper);
            User member3 = User.createUser("KAKAO","12316346523","조준장", "20184545",
                    "hunil9923@gmail.com","ajsk", Gender.MAN, null);
            User member4 = User.createUser("KAKAO","12316346523","이건희", "20184545",
                    "hunil9923@gmail.com","ajsk", Gender.WOMAN, null);
            User member5 = User.createUser("KAKAO","12316346523","김세준", "20184545",
                    "hunil9923@gmail.com","ajsk", Gender.MAN, null);


            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();

            em.clear();

        }

    }
}
