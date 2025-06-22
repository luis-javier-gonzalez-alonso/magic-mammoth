package magic.mammoth.model.meeples;

import magic.mammoth.model.board.Board;
import magic.mammoth.model.board.Cell;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.directions.Direction;
import magic.mammoth.model.directions.Orthogonals;
import magic.mammoth.model.movements.Movement;

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
    protected Movement power() {
        return (board, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            moveOneOrthogonal(board, origin, Orthogonals.Up)
                    .ifPresent(options::add);
            moveOneOrthogonal(board, origin, Orthogonals.Right)
                    .ifPresent(options::add);
            moveOneOrthogonal(board, origin, Orthogonals.Down)
                    .ifPresent(options::add);
            moveOneOrthogonal(board, origin, Orthogonals.Left)
                    .ifPresent(options::add);

            return options;
        };
    }

    private Optional<Coordinate> moveOneOrthogonal(Board board, Coordinate origin, Direction direction) {
        Coordinate destination = copyOf(origin).modify(direction);
        Cell from = board.get(origin);
        Cell next = board.get(destination);

        if (isBlocked(direction, from, next)) {
            return Optional.empty();
        }

        return Optional.of(destination);
    }

    private boolean isBlocked(Direction direction, Cell from, Cell next) {
        return from.checkAny(direction) || next.checkAny(direction.opposite());
    }
}
