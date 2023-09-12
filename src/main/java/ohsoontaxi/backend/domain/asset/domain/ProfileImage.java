package ohsoontaxi.backend.domain.asset.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.database.BaseEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ProfileImage extends BaseEntity {

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
