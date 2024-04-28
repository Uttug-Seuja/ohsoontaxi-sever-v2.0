package ohsoontaxi.backend.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.OauthTokenInfoDto;
import ohsoontaxi.backend.global.api.client.KakaoOauthClient;
import ohsoontaxi.backend.global.api.dto.OIDCPublicKeysResponse;
import ohsoontaxi.backend.global.api.dto.OauthTokenResponse;
import ohsoontaxi.backend.global.property.OauthProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("KAKAO")
@Slf4j
public class KaKaoOauthStrategy implements OauthStrategy{

    private final OauthProperties oauthProperties;
    private final KakaoOauthClient kakaoOauthClient;
    private final OauthOIDCProvider oauthOIDCProvider;
    private static final String ISSUER = "https://kauth.kakao.com";
    private static final String QUERY_STRING = "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";

    public String getOauthLink() {
        return oauthProperties.getKakaoBaseUrl()
                + String.format(
                QUERY_STRING,
                oauthProperties.getKakaoClientId(),
                oauthProperties.getKakaoRedirectUrl());
    }

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(
                token, ISSUER, oauthProperties.getKakaoAppId(), oidcPublicKeysResponse);
    }
}