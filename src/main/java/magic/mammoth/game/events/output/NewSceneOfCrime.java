package magic.mammoth.game.events.output;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import magic.mammoth.game.events.GameEvent;
import magic.mammoth.game.model.Coordinate;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class NewSceneOfCrime extends GameEvent {

    Coordinate sceneOfCrime;

    @Override
    public String getEventName() {
        return "scene-of-crime";
    }
}
