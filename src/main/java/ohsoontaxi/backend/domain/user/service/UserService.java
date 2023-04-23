package ohsoontaxi.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.presentation.dto.request.ChangeProfileRequest;
import ohsoontaxi.backend.domain.user.presentation.dto.response.UserProfileResponse;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtils userUtils;

    public UserProfileResponse changeProfilePath(ChangeProfileRequest changeProfileRequest){
        User user = userUtils.getUserFromSecurityContext();
        user.changeProfilePath(changeProfileRequest.getProfilePath());
        return new UserProfileResponse(user.getUserInfo());
    }

    public UserProfileResponse getProfile(){
        User user = userUtils.getUserFromSecurityContext();
        return  new UserProfileResponse(user.getUserInfo());
    }

}
