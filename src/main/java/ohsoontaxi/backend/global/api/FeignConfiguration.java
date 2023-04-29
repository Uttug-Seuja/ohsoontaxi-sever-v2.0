package ohsoontaxi.backend.global.api;

import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "ohsoontaxi.backend.global.api")
@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feginLoggerLevel() {
        return Logger.Level.FULL;
    }
}
