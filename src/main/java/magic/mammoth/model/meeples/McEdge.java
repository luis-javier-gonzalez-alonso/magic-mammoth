package magic.mammoth.model.meeples;

import magic.mammoth.model.Coordinate;
import magic.mammoth.model.board.Board;
import magic.mammoth.model.board.CellLimit;
import magic.mammoth.model.directions.Orthogonals;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static magic.mammoth.model.Coordinate.copyOf;

public class McEdge extends MutantMeeple {

    @Override
    public String name() {
        return "Mc Edge (yellow)";
    }

    @Override
    public Movement power() {
        return (board, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(skipBorder(board, origin, SuperSpeed.Up));
            options.addAll(skipBorder(board, origin, SuperSpeed.Right));
            options.addAll(skipBorder(board, origin, SuperSpeed.Down));
            options.addAll(skipBorder(board, origin, SuperSpeed.Left));

            return options;
        };
    }

    private Set<Coordinate> skipBorder(Board board, Coordinate origin, SuperSpeed movement) {
        return movement.apply(board, origin).stream()
                .filter(last -> board.get(last).checkAny(borderInMovementDirection(movement))) // skip border
                .map(c -> findOrthogonallyOppositeBorder(board, movement.getDirection(), c)) // TODO Go to opposite border
                .filter(board::cellIsEmpty) // don't skip other meeples
                .flatMap(next -> movement.apply(board, next).stream())
                .collect(Collectors.toSet());
    }

    private Predicate<CellLimit> borderInMovementDirection(SuperSpeed movement) {
        return limit -> limit.isBorder() && limit.is(movement.getDirection());
    }

    // TODO keep advancing until opposite border is found (avoid dead areas like A,A or R,R)
    private Coordinate findOrthogonallyOppositeBorder(Board board, Orthogonals direction, Coordinate current) {
        Coordinate opposite = switch (direction) {
            case Up -> copyOf(current).set(board.getLastRow(), current.getColumn());
            case Right -> copyOf(current).set(current.getRow(), 'A');
            case Down -> copyOf(current).set('A', current.getColumn());
            case Left -> copyOf(current).set(current.getRow(), board.getLastColumn());
        };
        while (!board.get(opposite).checkAny(limit -> limit.isBorder() && limit.is(direction.opposite()))) {
            opposite.modify(direction);
        }
        return opposite;
    }
}
