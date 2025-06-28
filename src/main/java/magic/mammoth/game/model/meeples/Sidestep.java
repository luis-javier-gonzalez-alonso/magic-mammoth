package magic.mammoth.game.model.meeples;

import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.board.Board;
import magic.mammoth.game.model.board.Cell;
import magic.mammoth.game.model.directions.Direction;
import magic.mammoth.game.model.directions.Orthogonals;
import magic.mammoth.game.model.movements.Movement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static magic.mammoth.game.model.Coordinate.copyOf;

public class Sidestep extends MutantMeeple {

    @Override
    public String name() {
        return "Sidestep (red)";
    }

    @Override
    protected Movement power() {
        return (game, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            moveOneOrthogonal(game.getBoard(), origin, Orthogonals.Up)
                    .ifPresent(options::add);
            moveOneOrthogonal(game.getBoard(), origin, Orthogonals.Right)
                    .ifPresent(options::add);
            moveOneOrthogonal(game.getBoard(), origin, Orthogonals.Down)
                    .ifPresent(options::add);
            moveOneOrthogonal(game.getBoard(), origin, Orthogonals.Left)
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
