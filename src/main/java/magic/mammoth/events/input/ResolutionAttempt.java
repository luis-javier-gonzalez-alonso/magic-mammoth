package magic.mammoth.events.input;

import magic.mammoth.events.GameEvent;

public record ResolutionAttempt(int movements) implements GameEvent {

    @Override
    public String getEventName() {
        return "resolution-attempt";
    }
}
