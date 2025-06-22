package magic.mammoth.model.meeples;

import lombok.extern.slf4j.Slf4j;
import magic.mammoth.model.Board;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;
import magic.mammoth.model.movements.SuperSpeed;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Stream.concat;

@Slf4j
public abstract class MutantMeeple {

    protected Coordinate position;

    public abstract String name();

    public abstract Movement power();

    public Set<Coordinate> possibleDestinations(Board board) {
        return concat(stream(SuperSpeed.values()), Stream.of(power()))
                .flatMap(m -> m.apply(board, position).stream())
                .collect(Collectors.toSet());
    }
}
