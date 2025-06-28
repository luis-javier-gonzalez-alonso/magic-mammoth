package magic.mammoth.game.events.output;

import magic.mammoth.game.events.GameEvent;
import magic.mammoth.game.model.Coordinate;

public record NewSceneOfCrime(Coordinate sceneOfCrime) implements GameEvent {

    @Override
    public String getEventName() {
        return "scene-of-crime";
    }
}
