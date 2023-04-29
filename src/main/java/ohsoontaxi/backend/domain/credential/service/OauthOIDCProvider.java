package ohsoontaxi.backend.domain.credential.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.global.api.dto.OIDCPublicKeyDto;
import ohsoontaxi.backend.global.api.dto.OIDCPublicKeysResponse;
import ohsoontaxi.backend.global.security.JwtOIDCProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthOIDCProvider {

    private final JwtOIDCProvider jwtOIDCProvider;

    private String getKidFromUnsignedIdToken(String token, String iss, String aud) {
        log.info(iss, aud);
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud);
    }

    public OIDCDecodePayload getPayloadFromIdToken(
            String token, String iss, String aud, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        log.info("provider token = {}",token);
        String kid = getKidFromUnsignedIdToken(token, iss, aud);
        log.info("provider kid = {}",kid);

        OIDCPublicKeyDto oidcPublicKeyDto =
                oidcPublicKeysResponse.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();
        log.info("oidcPublicKeyDto={}",oidcPublicKeyDto);

        return (OIDCDecodePayload)
                jwtOIDCProvider.getOIDCTokenBody(
                        token, oidcPublicKeyDto.getN(), oidcPublicKeyDto.getE());
    }
}