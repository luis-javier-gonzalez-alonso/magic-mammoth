package magic.mammoth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.SneakyThrows;
import magic.mammoth.events.GameEvent;
import magic.mammoth.model.meeples.MutantMeeple;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Player {

    private final String name;

    @JsonIgnore
    private final String apiKey = UUID.randomUUID().toString();

    /**
     * When a player gets to the target with a meeple,
     * that meeple is added to the team of the player.
     * <p>
     * A player cannot use a meeple that is already in its team.
     */
    private final Set<MutantMeeple> superTeam = new HashSet<>();

    @JsonIgnore
    private SseEmitter emitter;

    public SseEmitter createEmitter() {
        if (emitter != null) {
            try {
                emitter.completeWithError(new RuntimeException("Discarding previous emitter due to reconnection"));
            } catch (IllegalStateException exception) {
                // Connection closed
            }
        }
        emitter = new SseEmitter(Duration.ofMinutes(5).toMillis());
//        emitter.onCompletion(() -> this.setEmitter(null));
        return emitter;
    }

    @SneakyThrows
    public void send(GameEvent event) {
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(event.getEventName())
                        .data(event));
            } catch (IllegalStateException exception) {
                // Connection closed
                emitter = null;
            }
        }
    }
}
