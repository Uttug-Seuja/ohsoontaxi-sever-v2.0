package ohsoontaxi.backend.domain.asset.service;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.domain.ProfileImage;
import ohsoontaxi.backend.domain.asset.domain.repository.ProfileImageRepository;
import ohsoontaxi.backend.domain.asset.exception.ProfileImageNotFoundException;
import ohsoontaxi.backend.domain.asset.presentation.dto.ProfileImageDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService implements AssetUtils{

    private final ProfileImageRepository profileImageRepository;

    @Override
    public ProfileImageDto getRandomProfileImage() {
        ProfileImage profileImage = findProfileImage();
        return new ProfileImageDto(profileImage);
    }

    @Override
    public ProfileImage findProfileImage() {
        ProfileImage profileImage =
                profileImageRepository
                        .findRandomProfileImage()
                        .orElseThrow(() -> ProfileImageNotFoundException.EXCEPTION);
        return profileImage;
    }
}
