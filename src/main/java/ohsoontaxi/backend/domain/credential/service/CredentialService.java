package ohsoontaxi.backend.domain.credential.service;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.credential.presentation.dto.request.LoginRequestDto;
import ohsoontaxi.backend.domain.credential.presentation.dto.response.AccessTokenDto;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.security.JwtTokenProvider;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CredentialService {

    private final UserUtils userUtils;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AccessTokenDto login(Long userId){
        User user = userUtils.getUserById(userId);
        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
        return new AccessTokenDto(accessToken);
    }

    // TODO: 2023/04/23 회원가입 떄 온도 넣기 로직
    // TODO: 2023/04/23 회원가입 떄 기본 이미지 가져오기 로직

}

