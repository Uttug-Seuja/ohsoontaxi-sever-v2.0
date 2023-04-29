package ohsoontaxi.backend.domain.credential.service;

import lombok.AllArgsConstructor;
import ohsoontaxi.backend.global.api.client.GoogleAuthClient;
import ohsoontaxi.backend.global.api.dto.OIDCPublicKeysResponse;
import ohsoontaxi.backend.global.property.OauthProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("GOOGLE")
public class GoogleOauthStrategy implements OauthStrategy{

    private final OauthProperties oauthProperties;
    private final GoogleAuthClient googleAuthClient;
    private final OauthOIDCProvider oauthOIDCProvider;
    private static final String ISSUER = "https://accounts.google.com";

    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = googleAuthClient.getGoogleOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(
                token, ISSUER, oauthProperties.getGoogleAppId(), oidcPublicKeysResponse);
    }

}

