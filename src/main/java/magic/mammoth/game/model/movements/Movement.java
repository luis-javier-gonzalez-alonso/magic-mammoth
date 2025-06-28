package magic.mammoth.game.model.movements;

import magic.mammoth.game.Game;
import magic.mammoth.game.model.Coordinate;

import java.util.Set;

public interface Movement {
    Set<Coordinate> apply(Game game, Coordinate origin);
}
