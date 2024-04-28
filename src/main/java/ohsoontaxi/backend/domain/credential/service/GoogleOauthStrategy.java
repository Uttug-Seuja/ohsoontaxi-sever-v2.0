package ohsoontaxi.backend.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.OauthTokenInfoDto;
import ohsoontaxi.backend.global.api.client.GoogleAuthClient;
import ohsoontaxi.backend.global.api.dto.OIDCPublicKeysResponse;
import ohsoontaxi.backend.global.api.dto.OauthTokenResponse;
import ohsoontaxi.backend.global.property.OauthProperties;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@Component("GOOGLE")
public class GoogleOauthStrategy implements OauthStrategy{

    private final OauthProperties oauthProperties;
    private final GoogleAuthClient googleAuthClient;
    private final OauthOIDCProvider oauthOIDCProvider;
    private static final String ISSUER = "https://accounts.google.com";
    private static final String QUERY_STRING =
            "/o/oauth2/v2/auth?response_type=code&client_id=%s&scope=%s&redirect_uri=%s";
    private static final String scope = "https://www.googleapis.com/auth/userinfo.email";

    @Override
    public String getOauthLink() {
        return oauthProperties.getGoogleBaseUrl()
                + String.format(
                QUERY_STRING,
                oauthProperties.getGoogleAppId(),
                scope,
                oauthProperties.getGoogleRedirectUrl());
    }

    @Override
    public OauthTokenInfoDto getOauthToken(String code) {
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        OauthTokenResponse oauthTokenResponse = googleAuthClient
                .googleAuth(
                        decodedCode,
                        oauthProperties.getGoogleAppId(),
                        oauthProperties.getGoogleClientSecret(),
                        oauthProperties.getGoogleRedirectUrl());

        return OauthTokenInfoDto.builder()
                .idToken(oauthTokenResponse.getIdToken())
                .accessToken(oauthTokenResponse.getAccessToken())
                .build();
    }

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = googleAuthClient.getGoogleOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(
                token, ISSUER, oauthProperties.getGoogleAppId(), oidcPublicKeysResponse);
    }
}

