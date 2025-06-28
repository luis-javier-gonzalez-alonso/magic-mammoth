package magic.mammoth.game.events.output;

import magic.mammoth.game.Game;
import magic.mammoth.game.events.GameEvent;

public record GameStarted(Game game) implements GameEvent {

    @Override
    public String getEventName() {
        return "game-started";
    }
}
