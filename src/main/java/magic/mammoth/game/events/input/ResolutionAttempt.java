package magic.mammoth.game.events.input;

import magic.mammoth.game.events.GameEvent;

public record ResolutionAttempt(int movements) implements GameEvent {

    @Override
    public String getEventName() {
        return "resolution-attempt";
    }
}
