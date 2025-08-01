package magic.mammoth.game.model.meeples;

import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.movements.Movement;

import java.util.Set;

public class BlueBeamer extends MutantMeeple {

    @Override
    public String name() {
        return "Blue Beamer (blue)";
    }

    @Override
    protected Movement power() {
        return (game, origin) -> Set.of(
                Coordinate.of('D', 'D'),
                Coordinate.of('H', 'H'),
                Coordinate.of('K', 'K'),
                Coordinate.of('O', 'O'));
    }
}
