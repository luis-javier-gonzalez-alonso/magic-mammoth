package magic.mammoth.game.model.meeples;

import magic.mammoth.game.Game;
import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.board.CellLimit;
import magic.mammoth.game.model.movements.Movement;
import magic.mammoth.game.model.movements.SuperSpeed;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OzzyMosis extends MutantMeeple {

    @Override
    public String name() {
        return "Ozzy Mosis (gray)";
    }

    @Override
    protected Movement power() {
        return (game, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(skipFirstWall(game, origin, SuperSpeed.Up));
            options.addAll(skipFirstWall(game, origin, SuperSpeed.Right));
            options.addAll(skipFirstWall(game, origin, SuperSpeed.Down));
            options.addAll(skipFirstWall(game, origin, SuperSpeed.Left));

            return options;
        };
    }

    private Set<Coordinate> skipFirstWall(Game game, Coordinate origin, SuperSpeed movement) {
        return movement.apply(game, origin).stream()
                .filter(last -> !game.getBoard().get(last).checkAny(borderInMovementDirection(movement))) // don't skip border
                .map(c -> c.modify(movement.getDirection())) // skip first wall
                .filter(game.getBoard()::cellIsEmpty) // don't skip other meeples
                .flatMap(next -> movement.apply(game, next).stream())
                .collect(Collectors.toSet());
    }

    private Predicate<CellLimit> borderInMovementDirection(SuperSpeed movement) {
        return limit -> limit.isBorder() && limit.is(movement.getDirection());
    }
}
