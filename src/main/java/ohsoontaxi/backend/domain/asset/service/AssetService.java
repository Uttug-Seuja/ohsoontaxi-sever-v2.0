package ohsoontaxi.backend.domain.asset.service;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.domain.ProfileImage;
import ohsoontaxi.backend.domain.asset.domain.repository.ProfileImageRepository;
import ohsoontaxi.backend.domain.asset.exception.ProfileImageNotFoundException;
import ohsoontaxi.backend.domain.asset.presentation.dto.ProfileImageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetService implements AssetUtils{

    private final ProfileImageRepository profileImageRepository;

    @Override
    public ProfileImageDto getRandomProfileImage() {
        ProfileImage profileImage =
                profileImageRepository
                        .findRandomProfileImage()
                        .orElseThrow(() -> ProfileImageNotFoundException.EXCEPTION);
        return new ProfileImageDto(profileImage);
    }

    @Override
    public Boolean checkIsBasicProfile(String profileUrl){
        return profileImageRepository.existsByImageUrl(profileUrl);
    }

    @Transactional
    public void saveProfileImage(){
        ProfileImage image1 = ProfileImage.createProfileImage(
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C098f3311-eae2-4f51-8ae7-90b4fa0887fc.jpeg");
        ProfileImage image2 = ProfileImage.createProfileImage(
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cea98a33e-8cda-49e9-ae0a-5ec66f935cea.jpeg");
        ProfileImage image3 = ProfileImage.createProfileImage(
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Ceedc049d-b84e-427b-abb2-003f454af16d.jpeg");
        ProfileImage image4 = ProfileImage.createProfileImage(
                "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg");

        profileImageRepository.save(image1);
        profileImageRepository.save(image2);
        profileImageRepository.save(image3);
        profileImageRepository.save(image4);
    }

}
