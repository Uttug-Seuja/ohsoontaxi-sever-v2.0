package ohsoontaxi.backend.domain.credential.event;

import lombok.Builder;
import lombok.Getter;
import ohsoontaxi.backend.global.event.DomainEvent;

@Getter
@Builder
public class LogoutUserEvent implements DomainEvent {
    private final Long userId;
}
