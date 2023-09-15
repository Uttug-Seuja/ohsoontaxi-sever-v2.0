package ohsoontaxi.backend.domain.asset.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.presentation.dto.ProfileImageDto;
import ohsoontaxi.backend.domain.asset.service.AssetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로필 이미지", description = "프로필 이미지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asset")
public class AssetController {

    private final AssetService assetService;

    @PostMapping("save")
    public void saveProfileImage(){
        assetService.saveProfileImage();
    }

    @SecurityRequirements
    @Operation(summary = "랜덤으로 프로필 이미지 가져오기")
    @GetMapping("/random")
    public ProfileImageDto randomImage(){
        return assetService.getRandomProfileImage();
    }
}
