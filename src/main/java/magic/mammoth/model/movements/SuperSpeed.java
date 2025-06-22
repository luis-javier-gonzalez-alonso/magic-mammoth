package magic.mammoth.model.movements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import magic.mammoth.model.Board;
import magic.mammoth.model.Cell;
import magic.mammoth.model.CellLimit;
import magic.mammoth.model.Coordinate;

import java.util.Set;

import static magic.mammoth.model.Coordinate.copyOf;

@Getter
@AllArgsConstructor
public enum SuperSpeed implements Movement {

    Up(-1, 0),
    Right(0, 1),
    Down(1, 0),
    Left(0, -1);

    private int rowChange;
    private int columnChange;

    @Override
    public Set<Coordinate> apply(Board board, Coordinate origin) {
        Coordinate current = copyOf(origin);
        Coordinate next = copyOf(origin);
        next.modify(rowChange, columnChange);

        while (!isBlocked(board.get(current), board.get(next))) {
            next.modify(rowChange, columnChange);
            current.modify(rowChange, columnChange);
        }

        return Set.of(current);
    }

    // TODO check next is empty
    private boolean isBlocked(Cell from, Cell next) {
        return switch (this) {
            case Up -> from.check(CellLimit.BoardTop) || from.check(CellLimit.WallTop) ||
                    next.check(CellLimit.BoardBottom) || next.check(CellLimit.WallBottom);
            case Right -> from.check(CellLimit.BoardRight) || from.check(CellLimit.WallRight) ||
                    next.check(CellLimit.BoardLeft) || next.check(CellLimit.WallLeft);
            case Down -> from.check(CellLimit.BoardBottom) || from.check(CellLimit.WallBottom) ||
                    next.check(CellLimit.BoardTop) || next.check(CellLimit.WallTop);
            case Left -> from.check(CellLimit.BoardLeft) || from.check(CellLimit.WallLeft) ||
                    next.check(CellLimit.BoardRight) || next.check(CellLimit.WallRight);
        };
    }
}
