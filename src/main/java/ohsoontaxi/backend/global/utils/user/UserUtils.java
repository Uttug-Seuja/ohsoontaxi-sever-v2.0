package ohsoontaxi.backend.global.utils.user;

import ohsoontaxi.backend.domain.user.domain.User;

public interface UserUtils {

    User getUserById(Long id);

    User getUserFromSecurityContext();
}
