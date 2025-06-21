package magic.mammoth;

import magic.mammoth.model.Board;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.Direction;
import magic.mammoth.model.Piece;
import org.junit.jupiter.api.Test;

import static magic.mammoth.model.DefaultBoards.BASIC;
import static org.assertj.core.api.Assertions.assertThat;

public class PieceTests {

    @Test
    void movesUntilObject() {
        Board board = new Board(BASIC.getLastRow(), BASIC.getLastColumn(), BASIC.getTemplate(), BASIC.getLegend());
        Piece piece = new Piece("test-subject-001", Coordinate.of('C', 'C'));

        assertThat(piece.calculateDestination(board, Direction.Up))
                .isEqualTo(Coordinate.of('A', 'C'));
        assertThat(piece.calculateDestination(board, Direction.Right))
                .isEqualTo(Coordinate.of('C', 'H'));
        assertThat(piece.calculateDestination(board, Direction.Down))
                .isEqualTo(Coordinate.of('G', 'C'));
        assertThat(piece.calculateDestination(board, Direction.Left))
                .isEqualTo(Coordinate.of('C', 'C'));
    }
}
