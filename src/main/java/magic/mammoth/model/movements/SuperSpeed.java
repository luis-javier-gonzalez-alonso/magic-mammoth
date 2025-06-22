package magic.mammoth.model.movements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import magic.mammoth.model.board.Board;
import magic.mammoth.model.board.Cell;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.directions.Orthogonals;

import java.util.Set;

import static magic.mammoth.model.Coordinate.copyOf;

@Getter
@AllArgsConstructor
public enum SuperSpeed implements Movement {

    Up(Orthogonals.Up),
    Right(Orthogonals.Right),
    Down(Orthogonals.Down),
    Left(Orthogonals.Left);

    private Orthogonals direction;

    @Override
    public Set<Coordinate> apply(Board board, Coordinate origin) {
        Coordinate current = copyOf(origin);
        Coordinate next = copyOf(origin);
        next.modify(direction.getRowChange(), direction.getColumnChange());

        while (!isBlocked(board.get(current), board.get(next))) {
            next.modify(direction.getRowChange(), direction.getColumnChange());
            current.modify(direction.getRowChange(), direction.getColumnChange());
        }

        return Set.of(current);
    }

    private boolean isBlocked(Cell from, Cell next) {
        return isBlockedByLimits(from, next) || !next.isEmpty();
    }

    private boolean isBlockedByLimits(Cell from, Cell next) {
        return from.checkAny(direction) || next.checkAny(direction.opposite());
    }
}
