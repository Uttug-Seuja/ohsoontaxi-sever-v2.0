package ohsoontaxi.backend.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class OauthFactory {

    private final Map<String, OauthStrategy> oauthStrategyMap;

    public OauthStrategy getOauthstrategy(OauthProvider oauthProvider) {

        return oauthStrategyMap.get(oauthProvider.getValue());
    }
}

