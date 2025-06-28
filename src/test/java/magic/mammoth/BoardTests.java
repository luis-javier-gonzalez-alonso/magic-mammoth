package magic.mammoth;

import magic.mammoth.game.model.board.Board;
import magic.mammoth.game.model.board.CellLimit;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static magic.mammoth.game.model.board.BoardMode.BASIC;

public class BoardTests {

    @Test
    void boardLimits() {
        Board board = new Board('C', " ABA CDC ", Map.of(
                ' ', Set.of(),
                'B', Set.of(CellLimit.BoardTop, CellLimit.BoardRight),
                'C', Set.of(CellLimit.BoardRight, CellLimit.BoardBottom),
                'D', Set.of(CellLimit.BoardBottom, CellLimit.BoardLeft),
                'A', Set.of(CellLimit.BoardLeft, CellLimit.BoardTop)
        ));

        board.print();
    }

    @Test
    void boardWalls() {
        Board board = new Board('C', " ABA CDC ", Map.of(
                ' ', Set.of(),
                'B', Set.of(CellLimit.WallTop, CellLimit.WallRight),
                'C', Set.of(CellLimit.WallRight, CellLimit.WallBottom),
                'D', Set.of(CellLimit.WallBottom, CellLimit.WallLeft),
                'A', Set.of(CellLimit.WallLeft, CellLimit.WallTop)
        ));

        board.print();
    }

    @Test
    void basicBoard() {
        Board board = new Board(BASIC.getLastRowAndColumn(), BASIC.getTemplate(), BASIC.getLegend());

        board.print();
    }

//    @Test
//    void cantPassWallAndBorder() {
//        Board board = new Board('B', 'B', "0123", Map.of(
//                '0', Set.of(CellLimit.BoardLeft, CellLimit.WallTop),
//                '1', Set.of(CellLimit.BoardTop, CellLimit.WallRight),
//                '2', Set.of(CellLimit.WallLeft, CellLimit.BoardBottom),
//                '3', Set.of(CellLimit.WallBottom, CellLimit.BoardRight)));
//
////        SuperSpeed.Up.apply(board, )
//        SuperSpeed.Up.apply(board, Coordinate.of('A', 'A'));
//        SuperSpeed.Up.apply(board, Coordinate.of('A', 'B'));
//        SuperSpeed.Right.apply(board, Coordinate.of('A', 'B'));
//        SuperSpeed.Right.apply(board, Coordinate.of('B', 'B'));
//        SuperSpeed.Down.apply(board, Coordinate.of('B', 'B'));
//        SuperSpeed.Down.apply(board, Coordinate.of('B', 'A'));
//        SuperSpeed.Left.apply(board, Coordinate.of('B', 'A'));
//        SuperSpeed.Left.apply(board, Coordinate.of('A', 'A'));
//
//        SuperSpeed.Right.apply(board, Coordinate.of('A', 'A'));
//        SuperSpeed.Down.apply(board, Coordinate.of('A', 'B'));
//        SuperSpeed.Left.apply(board, Coordinate.of('B', 'B'));
//        SuperSpeed.Up        .apply(board, Coordinate.of('B', 'A'));
//
//
//
//
//
//
//
//
//
//
//
//        assertFalse(board.canMove(, ));
//        assertFalse(board.canMove(, ));
//        assertFalse(board.canMove(, ));
//        assertFalse(board.canMove(, ));
//        assertFalse(board.canMove(, ));
//        assertFalse(board.canMove(, ));
//        assertFalse(board.canMove(, ));
//        assertFalse(board.canMove(, ));
//
//        assertTrue(board.canMove(, ));
//        assertTrue(board.canMove(, ));
//        assertTrue(board.canMove(, ));
//        assertTrue(board.canMove(, ));
//    }

//    @Test
//    void basicMovements() {
//        Board board = new Board('A', 'D', "0012", Map.of(
//                '1', Set.of(CellLimit.WallRight),
//                '2', Set.of(CellLimit.BoardRight)));
//
//        assertTrue(board.canMove(Coordinate.of('A', 'A'), SuperSpeed.Right));
//        assertTrue(board.canMove(Coordinate.of('A', 'B'), SuperSpeed.Right));
//        assertFalse(board.canMove(Coordinate.of('A', 'C'), SuperSpeed.Right));
//        assertFalse(board.canMove(Coordinate.of('A', 'D'), SuperSpeed.Right));
//    }
}
