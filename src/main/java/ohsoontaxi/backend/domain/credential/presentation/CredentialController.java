package ohsoontaxi.backend.domain.credential.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.credential.presentation.dto.request.LoginRequestDto;
import ohsoontaxi.backend.domain.credential.service.CredentialService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credential")
@RequiredArgsConstructor
@Slf4j
public class CredentialController {

    private final CredentialService credentialService;

    @PostMapping("/login/{userId}")
    public String login(@PathVariable("userId") Long userId){
        String login = credentialService.login(userId);
        return login;
    }
}
