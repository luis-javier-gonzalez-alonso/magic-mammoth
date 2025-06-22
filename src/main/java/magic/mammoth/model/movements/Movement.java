package magic.mammoth.model.movements;

import magic.mammoth.model.Board;
import magic.mammoth.model.Coordinate;

import java.util.Set;

public interface Movement {
    Set<Coordinate> apply(Board board, Coordinate origin);
}
