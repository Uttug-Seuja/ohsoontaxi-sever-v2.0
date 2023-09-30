package ohsoontaxi.backend.domain.image.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.image.presentation.dto.UploadImageResponse;
import ohsoontaxi.backend.domain.image.service.ImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "업로드", description = "업로드 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "사진 업로드")
    @PostMapping("/upload")
    public UploadImageResponse uploadImage(
            @Parameter(name = "file",
                    description = "multipart/form-data 형식의 이미지 리스트를 input으로 받습니다. 이때 key 값은 file 입니다.",
                    required = true)
            @RequestPart MultipartFile file) {
        return imageService.uploadImage(file);
    }
}
