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

    private static double temperatureCalculation(Integer reportedCount, Integer participationCount) {
        int penalty = reportedCount * (reportedCount + 1) / 2;
        int bonus = 1 + participationCount / 10;

        double modifiedTemperature = BASE_TEMPERATURE - (penalty * reportedCount / 10.0) + (bonus * participationCount / 10.0);
        return Math.max(0, modifiedTemperature);
    }


    private static void updateTemperatureImage(Temperature temperature) {
        Double currentTemperature = temperature.getCurrentTemperature();
        String image;

        switch((int) (currentTemperature / 20)) {
            case 5, 4:
                image = "https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C3fad8504-31f5-4243-9aae-6d710cbdbdae.png";
                break;
            case 3:
                image = "https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C4388d892-76e4-4530-91f7-6f8eab7b2cc3.png";
                break;
            case 2:
                image = "https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7Cb34fae75-7946-4e5c-8176-5f7a63dcc120.png";
                break;
            case 1:
                image = "https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C21e880a0-f44a-487c-9958-b75b6a640ee7.png";
                break;
            default:
                image = "https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C3443f4ba-3560-4e5c-b3f2-eb0e9d5e8b5f.png";
                break;
        }

        temperature.updateTemperatureImage(image);
    }
}
