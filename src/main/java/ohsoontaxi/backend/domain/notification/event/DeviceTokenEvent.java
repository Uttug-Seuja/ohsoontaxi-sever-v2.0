package ohsoontaxi.backend.domain.notification.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.event.DomainEvent;

@Getter
@RequiredArgsConstructor
public class DeviceTokenEvent implements DomainEvent {

    private final User user;
}
