package magic.mammoth.game.model.meeples;

import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.board.Board;
import magic.mammoth.game.model.board.Cell;
import magic.mammoth.game.model.directions.Diagonals;
import magic.mammoth.game.model.directions.Direction;
import magic.mammoth.game.model.movements.Movement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static magic.mammoth.game.model.Coordinate.copyOf;

public class Skewt extends MutantMeeple {

    @Override
    public String name() {
        return "Skewt (white)";
    }

    @Override
    protected Movement power() {
        return (game, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            moveOneDiagonal(game.getBoard(), origin, Diagonals.UpLeft)
                    .ifPresent(options::add);
            moveOneDiagonal(game.getBoard(), origin, Diagonals.UpRight)
                    .ifPresent(options::add);
            moveOneDiagonal(game.getBoard(), origin, Diagonals.DownLeft)
                    .ifPresent(options::add);
            moveOneDiagonal(game.getBoard(), origin, Diagonals.DownRight)
                    .ifPresent(options::add);

            return options;
        };
    }

    private Optional<Coordinate> moveOneDiagonal(Board board, Coordinate origin, Diagonals direction) {
        return moveOneOrthogonal(board, origin, direction.getA())
                .flatMap(middle -> moveOneOrthogonal(board, middle, direction.getB()))
                .or(() -> moveOneOrthogonal(board, origin, direction.getB())
                        .flatMap(middle -> moveOneOrthogonal(board, middle, direction.getA())));

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
