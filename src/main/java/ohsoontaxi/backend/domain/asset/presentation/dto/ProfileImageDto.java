package ohsoontaxi.backend.domain.asset.presentation.dto;

import lombok.Getter;
import ohsoontaxi.backend.domain.asset.domain.ProfileImage;

@Getter
public class ProfileImageDto {

    private Long id;
    private String url;

    public ProfileImageDto(ProfileImage profileImage) {
        this.id = profileImage.getId();
        this.url = profileImage.getImageUrl();
    }
}
