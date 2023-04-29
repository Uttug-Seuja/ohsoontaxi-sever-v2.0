package ohsoontaxi.backend.global.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.credential.service.OIDCDecodePayload;
import ohsoontaxi.backend.global.exception.ExpiredTokenException;
import ohsoontaxi.backend.global.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtOIDCProvider {

    private final String KID = "kid";

    public String getKidFromUnsignedTokenHeader(String token, String iss, String aud) {

        String kid = (String) getUnsignedTokenClaims(token, iss, aud).getHeader().get(KID);
        log.info("kid = {}",kid);
        return kid;
    }

    private Jwt<Header, Claims> getUnsignedTokenClaims(String token, String iss, String aud) {
        try {
            Jwt<Header, Claims> headerClaimsJwt = Jwts.parserBuilder()
                    .requireAudience(aud)
                    .requireIssuer(iss)
                    .build()
                    .parseClaimsJwt(getUnsignedToken(token));
            log.info("headerClaimsJwt = {}", headerClaimsJwt);
            return headerClaimsJwt;

        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            log.error(e.toString());
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private String getUnsignedToken(String token) {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw InvalidTokenException.EXCEPTION;
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        log.info("unsignedToken = {}",unsignedToken);
        return unsignedToken;
    }

    public Jws<Claims> getOIDCTokenJws(String token, String modulus, String exponent) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getRSAPublicKey(modulus, exponent))
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            log.error(e.toString());
            throw InvalidTokenException.EXCEPTION;
        }
    }

    public OIDCDecodePayload getOIDCTokenBody(String token, String modulus, String exponent) {
        Claims body = getOIDCTokenJws(token, modulus, exponent).getBody();
        return new OIDCDecodePayload(
                body.getIssuer(),
                body.getAudience(),
                body.getSubject(),
                body.get("email", String.class));
    }

    private Key getRSAPublicKey(String modulus, String exponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);
    }
}