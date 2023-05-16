package ohsoontaxi.backend.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.asset.service.AssetUtils;
import ohsoontaxi.backend.domain.credential.domain.RefreshTokenRedisEntity;
import ohsoontaxi.backend.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import ohsoontaxi.backend.domain.credential.exception.RefreshTokenExpiredException;
import ohsoontaxi.backend.domain.credential.presentation.dto.request.RegisterRequest;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.AccessTokenDto;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.AuthTokensResponse;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.AvailableRegisterResponse;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.temperature.service.TemperatureUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.domain.repository.UserRepository;
import ohsoontaxi.backend.global.exception.InvalidTokenException;
import ohsoontaxi.backend.global.exception.UserNotFoundException;
import ohsoontaxi.backend.global.security.JwtTokenProvider;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CredentialService {

    private final KaKaoOauthStrategy kaKaoOauthStrategy;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisEntityRepository refreshTokenRedisEntityRepository;
    private final OauthFactory oauthFactory;
    private final UserUtils userUtils;
    private final AssetUtils assetUtils;
    private final TemperatureUtils temperatureUtils;

    @Transactional
    public AccessTokenDto login(Long userId){
        User user = userUtils.getUserById(userId);
        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
        return new AccessTokenDto(accessToken);
    }

    private String generateRefreshToken(Long userId) {
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);
        Long tokenExpiredAt = jwtTokenProvider.getRefreshTokenTTlSecond();
        RefreshTokenRedisEntity build =
                RefreshTokenRedisEntity.builder()
                        .id(userId.toString())
                        .ttl(tokenExpiredAt)
                        .refreshToken(refreshToken)
                        .build();
        refreshTokenRedisEntityRepository.save(build);
        return refreshToken;
    }

    private Boolean checkUserCanRegister(
            OIDCDecodePayload oidcDecodePayload, OauthProvider oauthProvider) {
        Optional<User> user =
                userRepository.findByOauthIdAndOauthProvider(
                        oidcDecodePayload.getSub(), oauthProvider.getValue());
        return user.isEmpty();
    }

    public AvailableRegisterResponse getUserAvailableRegister(String token, OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("service token = {}",token);
        OauthStrategy oauthstrategy = oauthFactory.getOauthstrategy(oauthProvider); //provider에 맞는 strategy 꺼내기
        OIDCDecodePayload oidcDecodePayload = oauthstrategy.getOIDCDecodePayload(token); //공개 키 가져와서 idtoken 검증
        Boolean isRegistered = !checkUserCanRegister(oidcDecodePayload, oauthProvider); //공개 키로 회원가입 유저인지 확인(true 이미 회원가입한 회원)
        return new AvailableRegisterResponse(isRegistered);
    }

    @Transactional
    public AuthTokensResponse registerUserByOCIDToken(
            String token, RegisterRequest registerUserRequest, OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        Temperature temperature = temperatureUtils.createTemperature();

        User newUser =
                User.builder()
                        .oauthProvider(oauthProvider.getValue())
                        .oauthId(oidcDecodePayload.getSub())
                        .email(oidcDecodePayload.getEmail())
                        .name(registerUserRequest.getName())
                        .gender(registerUserRequest.getGender())
                        .profilePath(registerUserRequest.getProfilePath())
                        .temperature(temperature)
                        .build();
        userRepository.save(newUser);

        String accessToken =
                jwtTokenProvider.generateAccessToken(newUser.getId(), newUser.getAccountRole());
        String refreshToken = generateRefreshToken(newUser.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthTokensResponse loginUserByOCIDToken(String token, OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        User user =
                userRepository
                        .findByOauthIdAndOauthProvider(
                                oidcDecodePayload.getSub(), oauthProvider.getValue())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getAccountRole());

        String refreshToken = generateRefreshToken(user.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthTokensResponse tokenRefresh(String requestRefreshToken) {

        Optional<RefreshTokenRedisEntity> entityOptional =
                refreshTokenRedisEntityRepository.findByRefreshToken(requestRefreshToken);

        RefreshTokenRedisEntity refreshTokenRedisEntity =
                entityOptional.orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);

        Long userId = jwtTokenProvider.parseRefreshToken(requestRefreshToken);

        if (!userId.toString().equals(refreshTokenRedisEntity.getId())) {
            throw InvalidTokenException.EXCEPTION;
        }

        User user = userUtils.getUserById(userId);

        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
        String refreshToken = generateRefreshToken(userId);

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void logoutUser() {
        User user = userUtils.getUserFromSecurityContext();
        refreshTokenRedisEntityRepository.deleteById(user.getId().toString());
    }
}

