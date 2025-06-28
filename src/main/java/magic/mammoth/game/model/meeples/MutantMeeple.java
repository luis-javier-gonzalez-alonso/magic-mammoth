package magic.mammoth.game.model.meeples;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magic.mammoth.game.Game;
import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.movements.Movement;
import magic.mammoth.game.model.movements.SuperSpeed;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Stream.concat;

@Slf4j
@NoArgsConstructor
public abstract class MutantMeeple {

    @JsonProperty
    protected Coordinate position;

    @JsonProperty
    public abstract String name();

    protected abstract Movement power();

    public Set<Coordinate> possibleDestinations(Game game) {
        return concat(stream(SuperSpeed.values()), Stream.of(power()))
                .flatMap(m -> m.apply(game, position).stream())
                .filter(game.getBoard()::outOfBounds)
                .filter(game.getBoard()::cellIsEmpty)
                .collect(Collectors.toSet());
    }

    public void moveTo(Game game, Coordinate newPosition) {
        if (position != null) {
            game.getBoard().get(position).setContent(null);
        }
        position = newPosition;
        game.getBoard().get(position).setContent(this);
    }
}
