package magic.mammoth.game.events.output;

import magic.mammoth.game.events.GameEvent;

public record PlayerJoined(String playerName) implements GameEvent {

    @Override
    public String getEventName() {
        return "player-joined";
    }
}
