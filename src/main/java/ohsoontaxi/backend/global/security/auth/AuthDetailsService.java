package ohsoontaxi.backend.global.security.auth;

import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.domain.repository.UserRepository;
import ohsoontaxi.backend.global.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findById(Long.valueOf(id))
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return new AuthDetails(user.getId().toString(), user.getAccountRole().getValue());
    }
}
