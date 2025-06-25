package magic.mammoth.events;

import magic.mammoth.model.Game;

public record GameStarted(Game game) implements GameEvent {

    @Override
    public String getEventName() {
        return "game-started";
    }
}
