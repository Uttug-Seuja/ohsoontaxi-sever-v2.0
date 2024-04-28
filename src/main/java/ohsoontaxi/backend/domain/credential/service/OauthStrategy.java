package ohsoontaxi.backend.domain.credential.service;

import ohsoontaxi.backend.domain.credential.presentation.dto.response.OauthTokenInfoDto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface OauthStrategy {
    String getOauthLink();

    OIDCDecodePayload getOIDCDecodePayload(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
