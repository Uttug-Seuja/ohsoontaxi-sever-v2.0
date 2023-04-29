package ohsoontaxi.backend.domain.credential.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface OauthStrategy {
    OIDCDecodePayload getOIDCDecodePayload(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
