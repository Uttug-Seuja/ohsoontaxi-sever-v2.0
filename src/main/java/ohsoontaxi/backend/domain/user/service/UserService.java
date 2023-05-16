package ohsoontaxi.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.service.AssetUtils;
import ohsoontaxi.backend.domain.image.service.ImageUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.presentation.dto.request.ChangeProfileRequest;
import ohsoontaxi.backend.domain.user.presentation.dto.response.UserProfileResponse;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtils userUtils;
    private final AssetUtils assetUtils;
    private final ImageUtils imageUtils;

    @Transactional
    public UserProfileResponse changeProfilePath(ChangeProfileRequest changeProfileRequest){
        User user = userUtils.getUserFromSecurityContext();
        deleteUserProfilePath(user.getProfilePath());
        String imageUrl = changeProfileRequest.getProfilePath();
        user.changeProfilePath(imageUrl);
        return new UserProfileResponse(user.getUserInfo());
    }

    public UserProfileResponse getOtherProfile(Long userId){
        User user = userUtils.getUserById(userId);
        return new UserProfileResponse(user.getUserInfo());
    }

    public UserProfileResponse getProfile(){
        User user = userUtils.getUserFromSecurityContext();
        return new UserProfileResponse(user.getUserInfo());
    }

    private void deleteUserProfilePath(String profilePath){
        if (!assetUtils.checkIsBasicProfile(profilePath)) {
            imageUtils.delete(profilePath);
        }
    }

}
