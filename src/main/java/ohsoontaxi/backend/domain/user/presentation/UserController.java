package ohsoontaxi.backend.domain.user.presentation;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.domain.ProfileImage;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.presentation.dto.request.ChangeProfileRequest;
import ohsoontaxi.backend.domain.user.presentation.dto.response.UserProfileResponse;
import ohsoontaxi.backend.domain.user.service.UserService;
import ohsoontaxi.backend.global.common.user.Gender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final EntityManager em;

    @PatchMapping("/profile")
    public UserProfileResponse changeProfilePath(@RequestBody @Valid ChangeProfileRequest changeProfileRequest){
        return userService.changeProfilePath(changeProfileRequest);
    }

    @GetMapping("/other-profile/{userId}")
    public UserProfileResponse getOtherProfile(@PathVariable Long userId){
        return userService.getOtherProfile(userId);
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile(){
        return userService.getProfile();
    }


    @PostMapping("/save")
    public void initUser(){


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
                "hun@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.MAN, temper1);
        User member2 = User.createUser("KAKAO","1231123123","김찬우",
                "hun1gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.WOMAN, temper);
        User member3 = User.createUser("KAKAO","12316346523","조준장",
                "hun2@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.MAN, temper2);
        User member4 = User.createUser("KAKAO","12316346523","이건희",
                "hun3@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.WOMAN, temper3);
        User member5 = User.createUser("KAKAO","12316346523","김세준",
                "hun4@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.MAN, temper4);
        User member6 = User.createUser("KAKAO","12316346523","김은지",
                "hun5@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.WOMAN, temper5);
        User member7 = User.createUser("KAKAO","12316346523","이은혜",
                "hun6@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.MAN, temper6);
        User member8 = User.createUser("KAKAO","12316346523","하재은",
                "hun7@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.WOMAN, temper7);
        User member9 = User.createUser("KAKAO","12316346523","김해나",
                "hun8@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.MAN, temper8);
        User member10 = User.createUser("KAKAO","12316346523","최유진",
                "hun9@gmail.com","https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg", Gender.WOMAN, temper9);

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

        em.flush();
        em.clear();

    }
 }
