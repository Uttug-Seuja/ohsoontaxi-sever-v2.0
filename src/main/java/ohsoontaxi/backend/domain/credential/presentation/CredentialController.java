package ohsoontaxi.backend.domain.credential.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@Tag(name = "로그인", description = "로그인 관련 API")
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

    @Operation(summary = "Id Token 검증")
    @GetMapping("/oauth/valid/register")
    @Parameters({
            @Parameter(name = "idToken", description = "idToken", required = true),
            @Parameter(name = "provider", description = "idToken 제공자", required = true)
    })
    public AvailableRegisterResponse valid(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("controller token = {}",token);
        return credentialService.getUserAvailableRegister(token, oauthProvider);
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/register")
    @Parameters({
            @Parameter(name = "idToken", description = "idToken", required = true),
            @Parameter(name = "provider", description = "idToken 제공자", required = true)
    })
    public AuthTokensResponse registerUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider,
            @Valid @RequestBody RegisterRequest registerRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return credentialService.registerUserByOCIDToken(token, registerRequest, oauthProvider);
    }

    @Operation(summary = "로그인")
    @Parameters({
            @Parameter(name = "idToken", description = "idToken", required = true),
            @Parameter(name = "provider", description = "idToken 제공자", required = true)
    })
    @PostMapping("/login")
    public AuthTokensResponse loginUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return credentialService.loginUserByOCIDToken(token, oauthProvider);
    }

    @Operation(summary = "토큰 리프레쉬")
    @PostMapping("/refresh")
    public AuthTokensResponse refreshingToken(
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return credentialService.tokenRefresh(tokenRefreshRequest.getRefreshToken());
    }

    @Operation(summary = "로그 아웃")
    @PostMapping("/logout")
    public void logout() {
        credentialService.logoutUser();
    }
}
