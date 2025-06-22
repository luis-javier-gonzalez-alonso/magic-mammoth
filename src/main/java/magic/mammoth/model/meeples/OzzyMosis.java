package magic.mammoth.model.meeples;

import magic.mammoth.model.Board;
import magic.mammoth.model.CellLimit;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OzzyMosis extends MutantMeeple {

    @Override
    public String name() {
        return "Ozzy Mosis (gray)";
    }

    @Override
    public Movement power() {
        return (board, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(skipFirstWall(board, origin, SuperSpeed.Up, CellLimit.BoardTop));
            options.addAll(skipFirstWall(board, origin, SuperSpeed.Right, CellLimit.BoardRight));
            options.addAll(skipFirstWall(board, origin, SuperSpeed.Down, CellLimit.BoardBottom));
            options.addAll(skipFirstWall(board, origin, SuperSpeed.Left, CellLimit.BoardLeft));

            return options;
        };
    }

    private Set<Coordinate> skipFirstWall(Board board, Coordinate origin, SuperSpeed direction, CellLimit border) {
        return direction.apply(board, origin).stream()
                .filter(last -> !board.get(last).check(border))
                .map(c -> c.modify(direction.getRowChange(), direction.getColumnChange())) // Skip first wall
//                .filter(c -> board.get(c)) TODO check is empty
                .flatMap(next -> direction.apply(board, next).stream())
                .collect(Collectors.toSet());
    }
}
