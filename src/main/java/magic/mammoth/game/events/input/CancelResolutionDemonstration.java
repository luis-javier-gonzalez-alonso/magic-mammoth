package magic.mammoth.game.events.input;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import magic.mammoth.game.events.GameEvent;
import magic.mammoth.game.model.Coordinate;

// TODO event content to be designed
@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class CancelResolutionDemonstration extends GameEvent {

    Coordinate sceneOfCrime;

    @Override
    public String getEventName() {
        return "resolution-demonstration";
    }
}