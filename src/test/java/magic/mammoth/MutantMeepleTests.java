package magic.mammoth;

import magic.mammoth.model.Board;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.meeples.MutantMeeple;
import magic.mammoth.model.movements.SuperSpeed;
import org.junit.jupiter.api.Test;

import static magic.mammoth.model.DefaultBoards.BASIC;

public class MutantMeepleTests {

    @Test
    void movesUntilObject() {
        Board board = new Board(BASIC.getLastRow(), BASIC.getLastColumn(), BASIC.getTemplate(), BASIC.getLegend());
        MutantMeeple mutantMeeple = new MutantMeeple("test-subject-001", Coordinate.of('C', 'C'));

        assertThat(mutantMeeple.calculateDestination(board, SuperSpeed.Up))
                .isEqualTo(Coordinate.of('A', 'C'));
        assertThat(mutantMeeple.calculateDestination(board, SuperSpeed.Right))
                .isEqualTo(Coordinate.of('C', 'H'));
        assertThat(mutantMeeple.calculateDestination(board, SuperSpeed.Down))
                .isEqualTo(Coordinate.of('G', 'C'));
        assertThat(mutantMeeple.calculateDestination(board, SuperSpeed.Left))
                .isEqualTo(Coordinate.of('C', 'C'));
    }
}
