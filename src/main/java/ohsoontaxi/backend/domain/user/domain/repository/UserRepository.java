package ohsoontaxi.backend.domain.user.domain.repository;


import ohsoontaxi.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
