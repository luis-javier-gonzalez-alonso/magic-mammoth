package magic.mammoth.model.meeples;

import magic.mammoth.model.Board;
import magic.mammoth.model.Cell;
import magic.mammoth.model.CellLimit;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static magic.mammoth.model.Coordinate.copyOf;

public class Sidestep extends MutantMeeple {

    @Override
    public String name() {
        return "Sidestep (red)";
    }

    @Override
    public Movement power() {
        return (board, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            moveOneOrthogonal(board, origin, SuperSpeed.Up)
                    .ifPresent(options::add);
            moveOneOrthogonal(board, origin, SuperSpeed.Right)
                    .ifPresent(options::add);
            moveOneOrthogonal(board, origin, SuperSpeed.Down)
                    .ifPresent(options::add);
            moveOneOrthogonal(board, origin, SuperSpeed.Left)
                    .ifPresent(options::add);

            return options;
        };
    }

    private Optional<Coordinate> moveOneOrthogonal(Board board, Coordinate origin, SuperSpeed direction) {
        Coordinate destination = copyOf(origin)
                .modify(direction.getRowChange(), direction.getColumnChange());

        if (isBlocked(direction, board.get(origin), board.get(destination))) {
            return Optional.empty();
        }

        return Optional.of(destination);
    }

    // TODO check next is empty
    private boolean isBlocked(SuperSpeed direction, Cell from, Cell next) {
        return switch (direction) {
            case Up -> from.check(CellLimit.BoardTop) || from.check(CellLimit.WallTop) ||
                    next.check(CellLimit.BoardBottom) || next.check(CellLimit.WallBottom);
            case Right -> from.check(CellLimit.BoardRight) || from.check(CellLimit.WallRight) ||
                    next.check(CellLimit.BoardLeft) || next.check(CellLimit.WallLeft);
            case Down -> from.check(CellLimit.BoardBottom) || from.check(CellLimit.WallBottom) ||
                    next.check(CellLimit.BoardTop) || next.check(CellLimit.WallTop);
            case Left -> from.check(CellLimit.BoardLeft) || from.check(CellLimit.WallLeft) ||
                    next.check(CellLimit.BoardRight) || next.check(CellLimit.WallRight);
        };
    }
}
