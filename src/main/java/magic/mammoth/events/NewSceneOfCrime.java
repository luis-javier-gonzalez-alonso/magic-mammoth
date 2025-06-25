package magic.mammoth.events;

import magic.mammoth.model.Coordinate;

public record NewSceneOfCrime(Coordinate sceneOfCrime) implements GameEvent {

    @Override
    public String getEventName() {
        return "scene-of-crime";
    }
}
