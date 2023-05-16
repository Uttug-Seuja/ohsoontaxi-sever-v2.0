package ohsoontaxi.backend.domain.asset.presentation;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.presentation.dto.ProfileImageDto;
import ohsoontaxi.backend.domain.asset.service.AssetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asset")
public class AssetController {

    private final AssetService assetService;

    @PostMapping("save")
    public void saveProfileImage(){
        assetService.saveProfileImage();
    }

    @GetMapping("/random")
    public ProfileImageDto randomImage(){
        return assetService.getRandomProfileImage();
    }
}
