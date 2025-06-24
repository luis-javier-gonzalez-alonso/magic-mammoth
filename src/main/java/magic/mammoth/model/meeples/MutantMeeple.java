package magic.mammoth.model.meeples;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.game.Game;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Stream.concat;

@Slf4j
@NoArgsConstructor
public abstract class MutantMeeple {

    protected Coordinate position;

    public abstract String name();

    protected abstract Movement power();

    public Set<Coordinate> possibleDestinations(Game game) {
        return concat(stream(SuperSpeed.values()), Stream.of(power()))
                .flatMap(m -> m.apply(game, position).stream())
                .filter(game.getBoard()::outOfBounds)
                .filter(game.getBoard()::cellIsEmpty)
                .filter(next -> !position.equals(next)) // current is not a valid next option movement (TODO but will always contain current meeple so it should be removed by cellIsEmpty)
                .collect(Collectors.toSet());
    }

    public void moveTo(Coordinate newPosition) {
        position = newPosition;
    }
}
