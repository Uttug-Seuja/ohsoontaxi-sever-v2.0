package ohsoontaxi.backend.domain.user.presentation;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.asset.domain.ProfileImage;
import ohsoontaxi.backend.domain.temperature.domain.Temperature;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.presentation.dto.request.ChangeProfileRequest;
import ohsoontaxi.backend.domain.user.presentation.dto.response.DeployTestDto;
import ohsoontaxi.backend.domain.user.presentation.dto.response.UserProfileResponse;
import ohsoontaxi.backend.domain.user.service.UserService;
import ohsoontaxi.backend.global.common.user.Gender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final EntityManager em;

    @PatchMapping("/profile")
    public UserProfileResponse changeProfilePath(@RequestBody @Valid ChangeProfileRequest changeProfileRequest){
        return userService.changeProfilePath(changeProfileRequest);
    }

    @GetMapping("/other-profile/{userId}")
    public UserProfileResponse getOtherProfile(@PathVariable Long userId){
        return userService.getOtherProfile(userId);
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile(){
        return userService.getProfile();
    }


    @PostMapping("/save")
    public DeployTestDto initUser(){

        return new DeployTestDto();

    }
 }
