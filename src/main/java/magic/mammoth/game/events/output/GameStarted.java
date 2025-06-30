package magic.mammoth.game.events.output;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import magic.mammoth.game.Game;
import magic.mammoth.game.events.GameEvent;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class GameStarted extends GameEvent {

    Game game;

    @Override
    public String getEventName() {
        return "game-started";
    }
}