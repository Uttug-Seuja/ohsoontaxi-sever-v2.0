package ohsoontaxi.backend.global.api.client;

import ohsoontaxi.backend.global.api.dto.OIDCPublicKeysResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "GoogleAuthClient", url = "https://www.googleapis.com/oauth2")
public interface GoogleAuthClient {

    @Cacheable(cacheNames = "GoogleOICD", cacheManager = "oidcCacheManager")
    @GetMapping("/v3/certs")
    OIDCPublicKeysResponse getGoogleOIDCOpenKeys();
}

