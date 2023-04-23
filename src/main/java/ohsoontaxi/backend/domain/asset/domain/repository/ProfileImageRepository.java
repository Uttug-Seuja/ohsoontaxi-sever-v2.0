package ohsoontaxi.backend.domain.asset.domain.repository;

import ohsoontaxi.backend.domain.asset.domain.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query(value = "SELECT * FROM profile_image order by RAND() limit 1", nativeQuery = true)
    Optional<ProfileImage> findRandomProfileImage();
}
