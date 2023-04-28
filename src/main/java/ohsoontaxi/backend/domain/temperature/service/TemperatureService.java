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

    private final UserUtils userUtils;

    public static final double BASE_TEMPERATURE = 36.5;

    @Override
    public Temperature createTemperature() {
        Temperature temperature = Temperature.createTemperature();

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
            case 4:
                image = "이미지5";
                break;
            case 3:
                image = "이미지4";
                break;
            case 2:
                image = "이미지3";
                break;
            case 1:
                image = "이미지2";
                break;
            default:
                image = "이미지1";
                break;
        }

        temperature.updateTemperatureImage(image);
    }
}
