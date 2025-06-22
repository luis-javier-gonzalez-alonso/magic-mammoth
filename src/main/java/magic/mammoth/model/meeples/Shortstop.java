package magic.mammoth.model.meeples;

import magic.mammoth.model.board.Board;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Shortstop extends MutantMeeple {

    @Override
    public String name() {
        return "Shortstop (brown)";
    }

    @Override
    protected Movement power() {
        return (board, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(stopOneBefore(board, origin, SuperSpeed.Up));
            options.addAll(stopOneBefore(board, origin, SuperSpeed.Right));
            options.addAll(stopOneBefore(board, origin, SuperSpeed.Down));
            options.addAll(stopOneBefore(board, origin, SuperSpeed.Left));

            return options;
        };
    }

    private Set<Coordinate> stopOneBefore(Board board, Coordinate origin, SuperSpeed movement) {
        return movement.apply(board, origin).stream()
                .filter(c -> !origin.equals(c)) // Only affects when there is change
                .map(c -> c.modify(movement.getDirection().opposite())) // Go one back
                .collect(Collectors.toSet());
    }
}
