package magic.mammoth.events;

import java.util.Map;

public class PlayerJoined extends GameEvent {

    public PlayerJoined(String playerName) {
        super("player-joined", Map.of("playerName", playerName));
    }
}
