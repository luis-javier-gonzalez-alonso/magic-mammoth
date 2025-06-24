package magic.mammoth.model.movements;

import magic.mammoth.model.Coordinate;
import magic.mammoth.model.game.Game;

import java.util.Set;

public interface Movement {
    Set<Coordinate> apply(Game game, Coordinate origin);
}
