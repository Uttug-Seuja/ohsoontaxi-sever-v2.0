package ohsoontaxi.backend.domain.temperature.domain.repository;


import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
}
