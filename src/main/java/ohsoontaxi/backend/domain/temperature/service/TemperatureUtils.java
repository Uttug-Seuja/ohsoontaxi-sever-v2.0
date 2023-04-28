package ohsoontaxi.backend.domain.temperature.service;

import ohsoontaxi.backend.domain.temperature.domain.Temperature;

public interface TemperatureUtils {

    void createTemperature();
    void temperaturePatch(Long userId);
}
