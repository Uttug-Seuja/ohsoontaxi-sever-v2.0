package ohsoontaxi.backend.domain.temperature.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.temperature.domain.repository.TemperatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TemperatureService implements TemperatureUtils{

    private final TemperatureRepository temperatureRepository;

    @Override
    public void temperaturePatch(Long userId) {

    }
}
