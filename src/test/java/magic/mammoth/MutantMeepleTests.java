package magic.mammoth;

import magic.mammoth.model.Coordinate;
import magic.mammoth.model.board.Board;
import magic.mammoth.model.game.Game;
import magic.mammoth.model.meeples.BlueBeamer;
import magic.mammoth.model.meeples.ForrestJump;
import magic.mammoth.model.meeples.McEdge;
import magic.mammoth.model.meeples.MutantMeeple;
import magic.mammoth.model.meeples.OzzyMosis;
import magic.mammoth.model.meeples.Shortstop;
import magic.mammoth.model.meeples.Sidestep;
import magic.mammoth.model.meeples.Skewt;
import magic.mammoth.model.movements.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static magic.mammoth.model.board.DefaultBoards.BASIC;
import static org.assertj.core.api.Assertions.assertThat;

public class MutantMeepleTests {

    Game game;
    Board board;
    MutantMeeple powerless;

    @BeforeEach
    void setUp() {
        game = new Game("1");
        board = new Board(BASIC.getLastRow(), BASIC.getLastColumn(), BASIC.getTemplate(), BASIC.getLegend());
        game.setBoard(board);
        powerless = new MutantMeeple() {
            @Override
            public String name() {
                return "Powerless (transparent)";
            }

            @Override
            public Movement power() {
                return (game, origin) -> Set.of();
            }
        };
    }

    @Test
    void superSpeed() {
        powerless.moveTo(Coordinate.of('C', 'C'));
        assertThat(powerless.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'));

        powerless.moveTo(Coordinate.of('E', 'E'));
        assertThat(powerless.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        Coordinate.of('A', 'E'),
                        Coordinate.of('E', 'B'),
                        Coordinate.of('E', 'G'),
                        Coordinate.of('H', 'E'));

        powerless.moveTo(Coordinate.of('H', 'K'));
        assertThat(powerless.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        Coordinate.of('G', 'K'),
                        Coordinate.of('H', 'E'));
    }

    @Test
    void forrestJump() {
        MutantMeeple meeple = new ForrestJump();

        // effect on border
        meeple.moveTo(Coordinate.of('A', 'B'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'E'),
                        Coordinate.of('K', 'B'),
                        // power
                        Coordinate.of('D', 'B'));

        meeple.moveTo(Coordinate.of('C', 'C'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'),
                        // power
                        Coordinate.of('C', 'F'),
                        Coordinate.of('F', 'C'));

        meeple.moveTo(Coordinate.of('I', 'I'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('E', 'I'),
                        Coordinate.of('I', 'D'),
                        Coordinate.of('I', 'N'),
                        Coordinate.of('P', 'I'),
                        // power
                        Coordinate.of('I', 'F'),
                        Coordinate.of('F', 'I'),
                        Coordinate.of('I', 'L'),
                        Coordinate.of('L', 'I'));
    }

    @Test
    void ozzyMosis() {
        MutantMeeple meeple = new OzzyMosis();

        // effect on border
        meeple.moveTo(Coordinate.of('A', 'B'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'E'),
                        Coordinate.of('K', 'B'),
                        // power
                        Coordinate.of('A', 'M'),
                        Coordinate.of('O', 'B'));

        meeple.moveTo(Coordinate.of('C', 'C'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'),
                        // power
                        Coordinate.of('C', 'A'),
                        Coordinate.of('J', 'C'),
                        Coordinate.of('C', 'R'));

        meeple.moveTo(Coordinate.of('I', 'I'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('E', 'I'),
                        Coordinate.of('I', 'D'),
                        Coordinate.of('I', 'N'),
                        Coordinate.of('P', 'I'),
                        // power
                        Coordinate.of('I', 'A'),
                        Coordinate.of('A', 'I'),
                        Coordinate.of('I', 'R'),
                        Coordinate.of('R', 'I'));
    }

    @Test
    void blueBeamer() {
        MutantMeeple meeple = new BlueBeamer();

        // effect on border
        meeple.moveTo(Coordinate.of('A', 'B'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'E'),
                        Coordinate.of('K', 'B'),
                        // power
                        Coordinate.of('D', 'D'),
                        Coordinate.of('H', 'H'),
                        Coordinate.of('K', 'K'),
                        Coordinate.of('O', 'O'));

        meeple.moveTo(Coordinate.of('C', 'C'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'),
                        // power
                        Coordinate.of('D', 'D'),
                        Coordinate.of('H', 'H'),
                        Coordinate.of('K', 'K'),
                        Coordinate.of('O', 'O'));

        meeple.moveTo(Coordinate.of('D', 'D'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('C', 'D'),
                        Coordinate.of('D', 'A'),
                        Coordinate.of('D', 'K'),
                        Coordinate.of('L', 'D'),
                        // power
                        Coordinate.of('H', 'H'),
                        Coordinate.of('K', 'K'),
                        Coordinate.of('O', 'O'));
    }

    @Test
    void shortstop() {
        MutantMeeple meeple = new Shortstop();

        // effect on border
        meeple.moveTo(Coordinate.of('A', 'B'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'E'),
                        Coordinate.of('K', 'B'),
                        // power
                        Coordinate.of('A', 'D'),
                        Coordinate.of('J', 'B'));

        meeple.moveTo(Coordinate.of('C', 'C'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'),
                        // power
                        Coordinate.of('B', 'C'),
                        Coordinate.of('C', 'G'),
                        Coordinate.of('F', 'C'));

        meeple.moveTo(Coordinate.of('D', 'D'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('C', 'D'),
                        Coordinate.of('D', 'A'),
                        Coordinate.of('D', 'K'),
                        Coordinate.of('L', 'D'),
                        // power
                        Coordinate.of('D', 'B'),
                        Coordinate.of('D', 'J'),
                        Coordinate.of('K', 'D'));
    }

    @Test
    void sidestep() {
        MutantMeeple meeple = new Sidestep();

        // effect on border
        meeple.moveTo(Coordinate.of('A', 'B'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'E'),
                        Coordinate.of('K', 'B'),
                        // power
                        Coordinate.of('A', 'C'),
                        Coordinate.of('B', 'B'));

        meeple.moveTo(Coordinate.of('C', 'C'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'),
                        // power
                        Coordinate.of('B', 'C'),
                        Coordinate.of('C', 'D'),
                        Coordinate.of('D', 'C'));

        meeple.moveTo(Coordinate.of('D', 'D'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('C', 'D'),
                        Coordinate.of('D', 'A'),
                        Coordinate.of('D', 'K'),
                        Coordinate.of('L', 'D'),
                        // power
                        Coordinate.of('D', 'C'),
                        Coordinate.of('D', 'E'),
                        Coordinate.of('E', 'D'));
    }

    @Test
    void skewt() {
        MutantMeeple meeple = new Skewt();

        // effect on border
        meeple.moveTo(Coordinate.of('A', 'B'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'E'),
                        Coordinate.of('K', 'B'),
                        // power
                        Coordinate.of('B', 'A'),
                        Coordinate.of('B', 'C'));

        meeple.moveTo(Coordinate.of('C', 'C'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'),
                        // power
                        Coordinate.of('B', 'B'),
                        Coordinate.of('D', 'B'),
                        Coordinate.of('D', 'D'));

        meeple.moveTo(Coordinate.of('K', 'G'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('K', 'A'),
                        Coordinate.of('R', 'G'),
                        // power
                        Coordinate.of('J', 'F'),
                        Coordinate.of('L', 'F'));
    }

    @Test
    void mcEdge() {
        MutantMeeple meeple = new McEdge();

        // effect on border
        meeple.moveTo(Coordinate.of('A', 'B'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'E'),
                        Coordinate.of('K', 'B'),
                        // power
                        Coordinate.of('A', 'N'),
                        Coordinate.of('P', 'B'));

        meeple.moveTo(Coordinate.of('C', 'C'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('A', 'C'),
                        Coordinate.of('C', 'H'),
                        Coordinate.of('G', 'C'),
                        // power
                        Coordinate.of('K', 'C'));

        meeple.moveTo(Coordinate.of('K', 'G'));
        assertThat(meeple.possibleDestinations(game))
                .containsExactlyInAnyOrder(
                        // super speed
                        Coordinate.of('K', 'A'),
                        Coordinate.of('R', 'G'),
                        // power
                        Coordinate.of('K', 'P'),
                        Coordinate.of('D', 'G'));
    }
}
