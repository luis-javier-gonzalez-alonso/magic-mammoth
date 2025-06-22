package magic.mammoth.model.meeples;

import magic.mammoth.model.Coordinate;
import magic.mammoth.model.board.Board;
import magic.mammoth.model.board.CellLimit;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

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
        return (board, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(skipFirstWall(board, origin, SuperSpeed.Up));
            options.addAll(skipFirstWall(board, origin, SuperSpeed.Right));
            options.addAll(skipFirstWall(board, origin, SuperSpeed.Down));
            options.addAll(skipFirstWall(board, origin, SuperSpeed.Left));

            return options;
        };
    }

    private Set<Coordinate> skipFirstWall(Board board, Coordinate origin, SuperSpeed movement) {
        return movement.apply(board, origin).stream()
                .filter(last -> !board.get(last).checkAny(borderInMovementDirection(movement))) // don't skip border
                .map(c -> c.modify(movement.getDirection())) // skip first wall
                .filter(board::cellIsEmpty) // don't skip other meeples
                .flatMap(next -> movement.apply(board, next).stream())
                .collect(Collectors.toSet());
    }

    private Predicate<CellLimit> borderInMovementDirection(SuperSpeed movement) {
        return limit -> limit.isBorder() && limit.is(movement.getDirection());
    }
}
