package magic.mammoth.model.meeples;

import magic.mammoth.model.Board;
import magic.mammoth.model.CellLimit;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class McEdge extends MutantMeeple {

    @Override
    public String name() {
        return "Mc Edge (yellow)";
    }

    @Override
    public Movement power() {
        return (board, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(skipBorder(board, origin, SuperSpeed.Up, CellLimit.BoardTop));
            options.addAll(skipBorder(board, origin, SuperSpeed.Right, CellLimit.BoardRight));
            options.addAll(skipBorder(board, origin, SuperSpeed.Down, CellLimit.BoardBottom));
            options.addAll(skipBorder(board, origin, SuperSpeed.Left, CellLimit.BoardLeft));

            return options;
        };
    }

    private Set<Coordinate> skipBorder(Board board, Coordinate origin, SuperSpeed direction, CellLimit border) {
        return direction.apply(board, origin).stream()
                .filter(last -> board.get(last).check(border))
                .map(c -> c.set(X, Y)) // TODO Go to opposite border
//                .filter(c -> board.get(c)) TODO check is empty
                .flatMap(next -> direction.apply(board, next).stream())
                .collect(Collectors.toSet());
    }
}
