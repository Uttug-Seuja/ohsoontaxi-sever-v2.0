package ohsoontaxi.backend.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.global.api.client.KakaoOauthClient;
import ohsoontaxi.backend.global.api.dto.OIDCPublicKeysResponse;
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

    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys(); //@feign을 이용해서 공개키 배열 가져오기(keys 리스트)
        return oauthOIDCProvider.getPayloadFromIdToken(
                token, ISSUER, oauthProperties.getKakaoAppId(), oidcPublicKeysResponse); //id token 검증
    }
}