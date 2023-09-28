package ohsoontaxi.backend.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.user.presentation.dto.request.ChangeProfileRequest;
import ohsoontaxi.backend.domain.user.presentation.dto.response.UserProfileResponse;
import ohsoontaxi.backend.domain.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 프로필 이미지 변경하기")
    @PatchMapping("/profile")
    public UserProfileResponse changeProfilePath(@Valid @RequestBody ChangeProfileRequest changeProfileRequest){
        return userService.changeProfilePath(changeProfileRequest);
    }

    @Operation(summary = "다른 유저 프로필 조회하기")
    @GetMapping("/other-profile/{userId}")
    public UserProfileResponse getOtherProfile(
            @Parameter(description = "유저 Id", in = PATH)
            @PathVariable Long userId){
        return userService.getOtherProfile(userId);
    }

    @Operation(summary = "내 프로필 조회하기")
    @GetMapping("/profile")
    public UserProfileResponse getProfile(){
        return userService.getProfile();
    }


    @PostMapping("/save")
    public void saveUser(){
        userService.saveUser();
    }
 }
