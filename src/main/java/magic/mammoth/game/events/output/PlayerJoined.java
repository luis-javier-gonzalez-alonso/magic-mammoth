package magic.mammoth.game.events.output;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import magic.mammoth.game.events.GameEvent;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class PlayerJoined extends GameEvent {

    String playerName;

    @Override
    public String getEventName() {
        return "player-joined";
    }
}