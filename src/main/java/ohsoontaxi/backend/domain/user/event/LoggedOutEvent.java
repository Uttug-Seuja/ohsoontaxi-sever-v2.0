package ohsoontaxi.backend.domain.user.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.event.DomainEvent;

@Getter
@RequiredArgsConstructor
public class LoggedOutEvent implements DomainEvent {
    private final User user;
}
