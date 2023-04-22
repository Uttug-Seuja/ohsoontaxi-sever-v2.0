package ohsoontaxi.backend.domain.temperature.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TemperatureInfoVo {

    private final Long temperatureId;
    private final Double currentTemperature;
    private final Integer reportedNum;
    private final Integer participationNum;
}
