package ohsoontaxi.backend.domain.user.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.service.AssetUtils;
import ohsoontaxi.backend.domain.chat.service.ChatRedisCacheService;
import ohsoontaxi.backend.domain.image.service.ImageUtils;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.temperature.service.TemperatureUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.presentation.dto.request.ChangeProfileRequest;
import ohsoontaxi.backend.domain.user.presentation.dto.response.UserProfileResponse;
import ohsoontaxi.backend.global.common.user.Gender;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtils userUtils;
    private final AssetUtils assetUtils;
    private final ImageUtils imageUtils;
    private final ChatRedisCacheService chatRedisCacheService;
    private final TemperatureUtils temperatureUtils;
    private final EntityManager em;

    @Transactional
    public UserProfileResponse changeProfilePath(ChangeProfileRequest changeProfileRequest){
        User user = userUtils.getUserFromSecurityContext();
        deleteUserProfilePath(user.getProfilePath());
        String imageUrl = changeProfileRequest.getProfilePath();
        user.changeProfilePath(imageUrl);
        chatRedisCacheService.changeUserCachingProfile(String.valueOf(user.getId()),imageUrl);
        return new UserProfileResponse(user.getUserInfo());
    }

    public UserProfileResponse getOtherProfile(Long userId){
        User user = userUtils.getUserById(userId);
        return new UserProfileResponse(user.getUserInfo());
    }

    public UserProfileResponse getProfile(){
        User user = userUtils.getUserFromSecurityContext();
        return new UserProfileResponse(user.getUserInfo());
    }

    private void deleteUserProfilePath(String profilePath){
        if (!assetUtils.checkIsBasicProfile(profilePath)) {
            imageUtils.delete(profilePath);
        }
    }

    @Transactional
    public void saveUser() {
        Temperature temperature1 = temperatureUtils.createTemperature();
        Temperature temperature2 = temperatureUtils.createTemperature();
        Temperature temperature3 = temperatureUtils.createTemperature();
        Temperature temperature4 = temperatureUtils.createTemperature();
        Temperature temperature5 = temperatureUtils.createTemperature();
        Temperature temperature6 = temperatureUtils.createTemperature();
        Temperature temperature7 = temperatureUtils.createTemperature();
        Temperature temperature8 = temperatureUtils.createTemperature();
        Temperature temperature9 = temperatureUtils.createTemperature();
        Temperature temperature10 = temperatureUtils.createTemperature();

        User user1 = User.createUser("KAKAO", "123115123", "이훈일", "hunil9978@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C51e20850-542c-4d73-9a35-7ae98375f5c6.jpeg", Gender.MAN, temperature1);
        User user2 = User.createUser("KAKAO", "112398759", "김동근", "tpwnsdl@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C51e20850-542c-4d73-9a35-7ae98375f5c6.jpeg", Gender.MAN, temperature2);
        User user3 = User.createUser("GOOGLE", "903589085", "강구영", "90000@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cae97fa26-ede4-451e-8fa3-adaea0c24c9e.jpeg", Gender.MAN, temperature3);
        User user4 = User.createUser("KAKAO", "230984738", "방시혁", "gungungun@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cae97fa26-ede4-451e-8fa3-adaea0c24c9e.jpeg", Gender.MAN, temperature4);
        User user5 = User.createUser("GOOGLE", "123677655", "김찬우", "chanwoo99@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Ca603afbc-cfc0-4d15-8c14-51b7c2cf674a.jpeg", Gender.MAN, temperature5);
        User user6 = User.createUser("KAKAO", "123677655", "하재은", "jaeajea@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Ca603afbc-cfc0-4d15-8c14-51b7c2cf674a.jpeg", Gender.WOMAN, temperature6);
        User user7 = User.createUser("KAKAO", "123130394", "김은지", "enujiqqq@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C737421e7-e595-4349-9ecb-d2363e821463.jpeg", Gender.WOMAN, temperature7);
        User user8 = User.createUser("GOOGLE", "123130394", "김미자", "mijada@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C737421e7-e595-4349-9ecb-d2363e821463.jpeg", Gender.WOMAN, temperature8);
        User user9 = User.createUser("KAKAO", "123130394", "박미리", "parkmiri@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C737421e7-e595-4349-9ecb-d2363e821463.jpeg", Gender.WOMAN, temperature9);
        User user10 = User.createUser("GOOGLE", "123677655", "이영희", "younghee@gmail.com",
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Ca603afbc-cfc0-4d15-8c14-51b7c2cf674a.jpeg", Gender.WOMAN, temperature10);

        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);
        em.persist(user6);
        em.persist(user7);
        em.persist(user8);
        em.persist(user9);
        em.persist(user10);
    }
}
