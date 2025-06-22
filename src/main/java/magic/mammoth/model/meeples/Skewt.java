package magic.mammoth.model.meeples;

import magic.mammoth.model.Board;
import magic.mammoth.model.Cell;
import magic.mammoth.model.CellLimit;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.movements.Movement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Predicate.not;
import static magic.mammoth.model.Coordinate.copyOf;

public class Skewt extends MutantMeeple {

    @Override
    public String name() {
        return "Skewt (white)";
    }

    @Override
    public Movement power() {
        return (board, origin) -> {
            Cell from = board.get(origin);
            Set<Coordinate> options = new HashSet<>();

            moveOneDiagonal(board, origin, from, -1, -1)
//                    .filter(c -> board.get(c).isEmpty()) TODO check is empty
                    .ifPresent(options::add);
            moveOneDiagonal(board, origin, from, -1, 1)
//                    .filter(c -> board.get(c).isEmpty()) TODO check is empty
                    .ifPresent(options::add);
            moveOneDiagonal(board, origin, from, 1, -1)
//                    .filter(c -> board.get(c).isEmpty()) TODO check is empty
                    .ifPresent(options::add);
            moveOneDiagonal(board, origin, from, 1, 1)
//                    .filter(c -> board.get(c).isEmpty()) TODO check is empty
                    .ifPresent(options::add);

            return options;
        };
    }

    private Optional<Coordinate> moveOneDiagonal(Board board, Coordinate origin, Cell from, int rowChange, int columnChange) {
        return Optional.of(copyOf(origin).modify(rowChange, columnChange))
                .filter(to -> !from.check(rowChange == -1 ? CellLimit.BoardTop : CellLimit.BoardBottom)
                        && !from.check(columnChange == -1 ? CellLimit.BoardLeft : CellLimit.BoardRight))
                .filter(not(to -> from.check(columnChange == -1 ? CellLimit.WallLeft : CellLimit.WallRight)
                        || board.get(to).check(rowChange == -1 ? CellLimit.WallBottom : CellLimit.WallTop)))
                .filter(not(to -> from.check(rowChange == 1 ? CellLimit.WallBottom : CellLimit.WallTop)
                        || board.get(to).check(columnChange == 1 ? CellLimit.WallLeft : CellLimit.WallRight)));
    }
}
