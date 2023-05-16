package ohsoontaxi.backend.domain.asset.presentation.dto;

import lombok.Getter;
import ohsoontaxi.backend.domain.asset.domain.ProfileImage;

@Getter
public class ProfileImageDto {

    private String url;

    public ProfileImageDto(ProfileImage profileImage) {
        this.url = profileImage.getImageUrl();
    }
}
