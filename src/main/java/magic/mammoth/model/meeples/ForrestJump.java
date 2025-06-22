package magic.mammoth.model.meeples;

import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static magic.mammoth.model.Coordinate.copyOf;

public class ForrestJump extends MutantMeeple {

    @Override
    public String name() {
        return "Forrest Jump (green)";
    }

    @Override
    public Movement power() {
        return (board, origin) -> {

            Coordinate up = copyOf(origin).modify(-3, 0);
            Coordinate right = copyOf(origin).modify(0, 3);
            Coordinate down = copyOf(origin).modify(3, 0);
            Coordinate left = copyOf(origin).modify(0, -3);

            return Stream.of(up, right, down, left)
//                    .filter(coordinate -> board.get(coordinate).isEmpty()); TODO filter is within limits
                    .collect(toSet());
        };
    }
}
