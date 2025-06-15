package magic.mammoth;

import magic.mammoth.model.Board;
import magic.mammoth.model.CellLimit;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class BoardTests {

    @Test
    void boardLimits() {
        Board board = new Board('C', 'C', " ABA CDC ", Map.of(
                ' ', List.of(),
                'B', List.of(CellLimit.BoardTop, CellLimit.BoardRight),
                'C', List.of(CellLimit.BoardRight, CellLimit.BoardBottom),
                'D', List.of(CellLimit.BoardBottom, CellLimit.BoardLeft),
                'A', List.of(CellLimit.BoardLeft, CellLimit.BoardTop)
        ));

        board.print();
    }

    @Test
    void boardWalls() {
        Board board = new Board('C', 'C', " ABA CDC ", Map.of(
                ' ', List.of(),
                'B', List.of(CellLimit.WallTop, CellLimit.WallRight),
                'C', List.of(CellLimit.WallRight, CellLimit.WallBottom),
                'D', List.of(CellLimit.WallBottom, CellLimit.WallLeft),
                'A', List.of(CellLimit.WallLeft, CellLimit.WallTop)
        ));

        board.print();
    }
}
