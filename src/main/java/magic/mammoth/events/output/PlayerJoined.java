package magic.mammoth.events.output;

import magic.mammoth.events.GameEvent;

public record PlayerJoined(String playerName) implements GameEvent {

    @Override
    public String getEventName() {
        return "player-joined";
    }
}
