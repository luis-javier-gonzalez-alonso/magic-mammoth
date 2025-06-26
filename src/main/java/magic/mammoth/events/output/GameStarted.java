package magic.mammoth.events.output;

import magic.mammoth.events.GameEvent;
import magic.mammoth.model.Game;

public record GameStarted(Game game) implements GameEvent {

    @Override
    public String getEventName() {
        return "game-started";
    }
}
