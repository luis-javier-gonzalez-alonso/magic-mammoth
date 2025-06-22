package magic.mammoth.model.meeples;

import magic.mammoth.model.movements.Movement;

import java.util.Set;

import static magic.mammoth.model.Coordinate.copyOf;

public class ForrestJump extends MutantMeeple {

    @Override
    public String name() {
        return "Forrest Jump (green)";
    }

    @Override
    protected Movement power() {
        return (board, origin) -> Set.of(
                copyOf(origin).modify(-3, 0),
                copyOf(origin).modify(0, 3),
                copyOf(origin).modify(3, 0),
                copyOf(origin).modify(0, -3));
    }
}
