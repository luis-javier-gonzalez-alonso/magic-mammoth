package magic.mammoth.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.SneakyThrows;
import magic.mammoth.events.GameEvent;
import magic.mammoth.model.meeples.MutantMeeple;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
    private SseEmitter emitter = new SseEmitter();

    @SneakyThrows
    public void send(GameEvent event) {
        emitter.send(event);
    }
}
