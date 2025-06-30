package magic.mammoth.game.events.input;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import magic.mammoth.game.events.GameEvent;

// TODO event content to be designed
@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class PactSkipTarget extends GameEvent {

    @Override
    public String getEventName() {
        return "pact-skip-target";
    }
}