package magic.mammoth.game.model.movements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import magic.mammoth.game.Game;
import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.board.Cell;
import magic.mammoth.game.model.directions.Orthogonals;

import java.util.Set;

import static magic.mammoth.game.model.Coordinate.copyOf;

@Getter
@AllArgsConstructor
public enum SuperSpeed implements Movement {

    Up(Orthogonals.Up),
    Right(Orthogonals.Right),
    Down(Orthogonals.Down),
    Left(Orthogonals.Left);

    private Orthogonals direction;

    @Override
    public Set<Coordinate> apply(Game game, Coordinate origin) {
        Coordinate current = copyOf(origin);
        Coordinate next = copyOf(origin);
        next.modify(direction.getRowChange(), direction.getColumnChange());

        while (!isBlocked(game.getBoard().get(current), game.getBoard().get(next))) {
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
