package ohsoontaxi.backend.domain.asset.service;

import ohsoontaxi.backend.domain.asset.presentation.dto.ProfileImageDto;

public interface AssetUtils {

    ProfileImageDto getRandomProfileImage();

    Boolean checkIsBasicProfile(String profileUrl);
}
