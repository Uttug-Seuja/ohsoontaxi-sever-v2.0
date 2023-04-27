package ohsoontaxi.backend.domain.image.presentation;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.image.presentation.dto.UploadImageResponse;
import ohsoontaxi.backend.domain.image.service.ImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public UploadImageResponse uploadImage(@RequestPart MultipartFile file) {
        return imageService.uploadImage(file);
    }
}
