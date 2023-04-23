package ohsoontaxi.backend.domain.asset.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
