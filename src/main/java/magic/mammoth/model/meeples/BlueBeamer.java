package magic.mammoth.model.meeples;

import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlueBeamer extends MutantMeeple {

    @Override
    public String name() {
        return "Blue Beamer (blue)";
    }

    @Override
    public Movement power() {
        return (board, origin) -> Stream.of(
                        Coordinate.of('D', 'D'),
                        Coordinate.of('H', 'H'),
                        Coordinate.of('K', 'K'),
                        Coordinate.of('O', 'O'))
//                .filter(c -> board.get(c).isEmpty()) TODO check is empty
                .collect(Collectors.toSet());
    }
}
