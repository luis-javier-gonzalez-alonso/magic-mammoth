package magic.mammoth.game.events.input;

import magic.mammoth.game.events.GameEvent;

// TODO event content to be designed
public record ResolutionDemonstration(/* define this event content*/) implements GameEvent {

    @Override
    public String getEventName() {
        return "resolution-demonstration";
    }
}
