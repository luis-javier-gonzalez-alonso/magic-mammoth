package magic.mammoth.game.model.meeples;

import magic.mammoth.game.Game;
import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.board.CellLimit;
import magic.mammoth.game.model.directions.Orthogonals;
import magic.mammoth.game.model.movements.Movement;
import magic.mammoth.game.model.movements.SuperSpeed;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static magic.mammoth.game.model.Coordinate.copyOf;

public class McEdge extends MutantMeeple {

    @Override
    public String name() {
        return "Mc Edge (yellow)";
    }

    @Override
    public Movement power() {
        return (game, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(skipBorder(game, origin, SuperSpeed.Up));
            options.addAll(skipBorder(game, origin, SuperSpeed.Right));
            options.addAll(skipBorder(game, origin, SuperSpeed.Down));
            options.addAll(skipBorder(game, origin, SuperSpeed.Left));

            return options;
        };
    }

    private Set<Coordinate> skipBorder(Game game, Coordinate origin, SuperSpeed movement) {
        return movement.apply(game, origin).stream()
                .filter(last -> game.getBoard().get(last).checkAny(borderInMovementDirection(movement))) // skip border
                .map(c -> findOrthogonallyOppositeBorder(game, movement.getDirection(), c))
                .filter(game.getBoard()::cellIsEmpty) // don't skip other meeples
                .flatMap(next -> movement.apply(game, next).stream())
                .collect(Collectors.toSet());
    }

    private Predicate<CellLimit> borderInMovementDirection(SuperSpeed movement) {
        return limit -> limit.isBorder() && limit.is(movement.getDirection());
    }

    private Coordinate findOrthogonallyOppositeBorder(Game game, Orthogonals direction, Coordinate current) {
        Coordinate opposite = switch (direction) {
            case Up -> copyOf(current).set(game.getBoard().getLastRowAndColumn(), current.getColumn());
            case Right -> copyOf(current).set(current.getRow(), 'A');
            case Down -> copyOf(current).set('A', current.getColumn());
            case Left -> copyOf(current).set(current.getRow(), game.getBoard().getLastRowAndColumn());
        };
        while (!game.getBoard().get(opposite).checkAny(limit -> limit.isBorder() && limit.is(direction.opposite()))) {
            opposite.modify(direction);
        }
        return opposite;
    }
}
