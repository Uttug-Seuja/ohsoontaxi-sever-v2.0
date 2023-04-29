package ohsoontaxi.backend.global.config;

import ohsoontaxi.backend.global.property.JwtProperties;
import ohsoontaxi.backend.global.property.OauthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class, OauthProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {}