package magic.mammoth.events;

import magic.mammoth.model.Coordinate;

import java.util.Map;

public class NewSceneOfCrime extends GameEvent {

    public NewSceneOfCrime(Coordinate sceneOfCrime) {
        super("scene-of-crime", Map.of("sceneOfCrime", sceneOfCrime));
    }
}
