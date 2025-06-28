package magic.mammoth.game.model.meeples;

import magic.mammoth.game.Game;
import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.movements.Movement;
import magic.mammoth.game.model.movements.SuperSpeed;

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
        return (game, origin) -> {
            Set<Coordinate> options = new HashSet<>();

            options.addAll(stopOneBefore(game, origin, SuperSpeed.Up));
            options.addAll(stopOneBefore(game, origin, SuperSpeed.Right));
            options.addAll(stopOneBefore(game, origin, SuperSpeed.Down));
            options.addAll(stopOneBefore(game, origin, SuperSpeed.Left));

            return options;
        };
    }

    private Set<Coordinate> stopOneBefore(Game game, Coordinate origin, SuperSpeed movement) {
        return movement.apply(game, origin).stream()
                .filter(c -> !origin.equals(c)) // Only affects when there is change
                .map(c -> c.modify(movement.getDirection().opposite())) // Go one back
                .collect(Collectors.toSet());
    }
}
