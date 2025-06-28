package magic.mammoth.game.model.meeples;

import magic.mammoth.game.model.movements.Movement;

import java.util.Set;

import static magic.mammoth.game.model.Coordinate.copyOf;

public class ForrestJump extends MutantMeeple {

    @Override
    public String name() {
        return "Forrest Jump (green)";
    }

    @Override
    protected Movement power() {
        return (game, origin) -> Set.of(
                copyOf(origin).modify(-3, 0),
                copyOf(origin).modify(0, 3),
                copyOf(origin).modify(3, 0),
                copyOf(origin).modify(0, -3));
    }
}
