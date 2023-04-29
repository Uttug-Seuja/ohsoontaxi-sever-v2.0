package ohsoontaxi.backend.domain.credential.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.credential.presentation.dto.request.LoginRequestDto;
import ohsoontaxi.backend.domain.credential.presentation.dto.request.RegisterRequest;
import ohsoontaxi.backend.domain.credential.presentation.dto.request.TokenRefreshRequest;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.AccessTokenDto;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.AuthTokensResponse;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.AvailableRegisterResponse;
import ohsoontaxi.backend.domain.credential.service.CredentialService;
import ohsoontaxi.backend.domain.credential.service.OauthProvider;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
@Slf4j
public class CredentialController {

    private final CredentialService credentialService;

    @PostMapping("/login2/{userId}")
    public AccessTokenDto login(@PathVariable("userId") Long userId){
        AccessTokenDto result = credentialService.login(userId);
        return result;
    }

    //id token 검증
    @GetMapping("/oauth/valid/register")
    public AvailableRegisterResponse valid(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("controller token = {}",token);
        return credentialService.getUserAvailableRegister(token, oauthProvider);
    }

    //회원가입
    @PostMapping("/register")
    public AuthTokensResponse registerUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider,
            @RequestBody RegisterRequest registerRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return credentialService.registerUserByOCIDToken(token, registerRequest, oauthProvider);
    }

    //로그인
    @PostMapping("/login")
    public AuthTokensResponse loginUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return credentialService.loginUserByOCIDToken(token, oauthProvider);
    }

    //토큰 리프레쉬
    @PostMapping("/refresh")
    public AuthTokensResponse refreshingToken(
            @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return credentialService.tokenRefresh(tokenRefreshRequest.getRefreshToken());
    }

    //로그아웃
    @PostMapping("/logout")
    public void logout() {
        credentialService.logoutUser();
    }
}
