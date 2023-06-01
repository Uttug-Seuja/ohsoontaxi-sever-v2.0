package ohsoontaxi.backend.domain.temperature.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.temperature.domain.repository.TemperatureRepository;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TemperatureService implements TemperatureUtils{

    private final TemperatureRepository temperatureRepository;
    private final UserUtils userUtils;

    public static final double BASE_TEMPERATURE = 36.5;

    @Override
    public Temperature createTemperature() {
        Temperature temperature = Temperature.createTemperature();

        temperatureRepository.save(temperature);

        return temperature;
    }

    @Override
    public void temperaturePatch(Long userId) {
        User currentUser = userUtils.getUserById(userId);
        Temperature currentUserTemperature = currentUser.getTemperature();

        Integer reportedNum = currentUserTemperature.getReportedNum();
        Integer participationNum = currentUserTemperature.getParticipationNum();

        double patchTemperature = temperatureCalculation(reportedNum, participationNum);

        currentUserTemperature.updateTemperature(patchTemperature);

        updateTemperatureImage(currentUserTemperature);
    }

    private static double temperatureCalculation(Integer reportedNum, Integer participationNum) {
        int penalty = reportedNum * (reportedNum + 1) / 2;
        int bonus = 1 + participationNum / 10;

        double modifiedTemperature = BASE_TEMPERATURE - ((double) (penalty * reportedNum) / 10) + ((double) (bonus * participationNum) / 10);

        return modifiedTemperature;
    }

    private static void updateTemperatureImage(Temperature temperature) {
        Double currentTemperature = temperature.getCurrentTemperature();
        String image;

        switch((int) (currentTemperature / 20)) {
            case 5, 4:
                image = "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C7491b506-606c-40de-9053-84c86b3b1f5b.png";
                break;
            case 3:
                image = "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C1685e865-278c-4e74-8f2e-8c6d5daf2fa1.png";
                break;
            case 2:
                image = "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C3a0f6539-4467-48c3-b4b6-2b27d0126be6.png";
                break;
            case 1:
                image = "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C31d30651-c9a3-4d77-a2d1-8f21c95ce400.png";
                break;
            default:
                image = "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Ca6caee77-23c3-46b7-80d6-49b56d76a8f2.png";
                break;
        }

        temperature.updateTemperatureImage(image);
    }
}
