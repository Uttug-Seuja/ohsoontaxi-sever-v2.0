package ohsoontaxi.backend.global.utils.user;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.domain.repository.UserRepository;
import ohsoontaxi.backend.global.exception.UserNotFoundException;
import ohsoontaxi.backend.global.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserUtilsImpl implements UserUtils{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public User getUserFromSecurityContext() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        return user;
    }
}
