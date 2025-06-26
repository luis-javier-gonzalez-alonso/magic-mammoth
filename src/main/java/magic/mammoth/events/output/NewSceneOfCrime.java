package magic.mammoth.events.output;

import magic.mammoth.events.GameEvent;
import magic.mammoth.model.Coordinate;

public record NewSceneOfCrime(Coordinate sceneOfCrime) implements GameEvent {

    @Override
    public String getEventName() {
        return "scene-of-crime";
    }
}
