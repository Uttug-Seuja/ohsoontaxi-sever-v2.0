package ohsoontaxi.backend.global.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
public class Events {
    private static ThreadLocal<ApplicationEventPublisher> publisherLocal = new ThreadLocal<>();

    public static void raise(DomainEvent event) {
        if (event == null) return;

        if (publisherLocal.get() != null) {
            publisherLocal.get().publishEvent(event);
        }
    }

    static void setPublisher(ApplicationEventPublisher publisher) {
        publisherLocal.set(publisher);
    }

    static void reset() {
        publisherLocal.remove();
    }
}
