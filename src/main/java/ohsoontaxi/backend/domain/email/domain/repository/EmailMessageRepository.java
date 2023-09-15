package ohsoontaxi.backend.domain.email.domain.repository;

import ohsoontaxi.backend.domain.email.domain.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, Long> {
    Boolean existsByEmail(String email);
    Optional<EmailMessage> findByEmail(String email);
    List<EmailMessage> findByLastModifyDateLessThan(LocalDateTime now);
    Optional<EmailMessage> findByOauthProviderAndEmail(String oauthProvider, String email);
}


