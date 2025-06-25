package magic.mammoth.events;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Set;

import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

public abstract class GameEvent {

    private final Set<ResponseBodyEmitter.DataWithMediaType> event;

    public GameEvent(String eventName, Object eventData) {
        this.event = event()
                .name(eventName)
                .data(eventData)
                .build();
    }

    public Set<ResponseBodyEmitter.DataWithMediaType> asSseEvent() {
        return event;
    }
}
