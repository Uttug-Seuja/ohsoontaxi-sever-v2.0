package ohsoontaxi.backend.domain.asset.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ProfileImage {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "profile_image_id")
    private Long id;

    private String imageUrl;

    @Builder
    public ProfileImage(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public static ProfileImage createProfileImage(String imageUrl) {
        return builder()
                .imageUrl(imageUrl)
                .build();
    }

}
