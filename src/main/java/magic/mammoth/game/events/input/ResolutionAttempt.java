package magic.mammoth.game.events.input;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import magic.mammoth.game.events.GameEvent;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class ResolutionAttempt extends GameEvent {

    int movements;

    @Override
    public String getEventName() {
        return "resolution-attempt";
    }
}