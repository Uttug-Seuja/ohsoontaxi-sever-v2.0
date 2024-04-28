package ohsoontaxi.backend.global.api.client;

import feign.Headers;
import ohsoontaxi.backend.global.api.dto.OIDCPublicKeysResponse;
import ohsoontaxi.backend.global.api.dto.OauthTokenResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "GoogleAuthClient", url = "https://www.googleapis.com/oauth2")
public interface GoogleAuthClient {

    @Cacheable(cacheNames = "GoogleOICD", cacheManager = "oidcCacheManager")
    @GetMapping("/v3/certs")
    OIDCPublicKeysResponse getGoogleOIDCOpenKeys();

    @Headers("Content-type: application/x-www-form-urlencoded;charset=utf-8")
    @PostMapping(
            "/v4/token?code={CODE}&client_id={CLIENT_ID}&client_secret={CLIENT_SECRET}&redirect_uri={REDIRECT_URI}&grant_type=authorization_code")
    OauthTokenResponse googleAuth(
            @PathVariable("CODE") String code,
            @PathVariable("CLIENT_ID") String clientId,
            @PathVariable("CLIENT_SECRET") String client_secret,
            @PathVariable("REDIRECT_URI") String redirectUri);
}

