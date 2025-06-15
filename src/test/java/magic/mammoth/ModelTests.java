//package magic.mammoth;
//
//import magic.mammoth.model.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.function.Predicate;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.params.provider.Arguments.arguments;
//
//public class ModelTests {
//
//    @Test
//    void coordinatesTest() {
//        Coordinate first = Coordinate.of('A', 'A');
//        assertThat(first.up()).isEqualTo(first);
//        assertThat(first.right()).isEqualTo(Coordinate.of('A', 'B'));
//        assertThat(first.down()).isEqualTo(Coordinate.of('B', 'A'));
//        assertThat(first.left()).isEqualTo(first);
//
//        Coordinate last = Coordinate.of('R', 'R');
//        assertThat(last.up()).isEqualTo(Coordinate.of('Q', 'R'));
//        assertThat(last.right()).isEqualTo(last);
//        assertThat(last.down()).isEqualTo(last);
//        assertThat(last.left()).isEqualTo(Coordinate.of('R', 'Q'));
//    }
//
//    @Test
//    void basicBoard() {
//        Board basic = new Board(DefaultBoardModes.BASIC);
//
//        assertThat(basic)
//                .extracting(b -> b.get(Coordinate.of('E', 'I')),
//                        b -> b.get(Coordinate.of('G', 'K')),
//                        b -> b.get(Coordinate.of('L', 'L')),
//                        b -> b.get(Coordinate.of('O', 'A')),
//                        b -> b.get(Coordinate.of('R', 'P')))
//                .allMatch(c -> c instanceof Cell cell
//                        && cell.isWallUp());
//
//        assertThat(basic)
//                .extracting(b -> b.get(Coordinate.of('A', 'F')),
//                        b -> b.get(Coordinate.of('C', 'I')),
//                        b -> b.get(Coordinate.of('H', 'E')),
//                        b -> b.get(Coordinate.of('J', 'Q')),
//                        b -> b.get(Coordinate.of('M', 'J')))
//                .allMatch(c -> c instanceof Cell cell
//                        && cell.isWallLeft());
//    }
//
//    @Test
//    void advancedBoard() {
//        Board advanced = new Board(DefaultBoardModes.ADVANCED);
//
//        assertThat(advanced)
//                .extracting(b -> b.get(Coordinate.of('C', 'C')),
//                        b -> b.get(Coordinate.of('H', 'D')),
//                        b -> b.get(Coordinate.of('O', 'E')))
//                .allMatch(c -> c instanceof Cell cell && cell.isWallUp());
//
//        assertThat(advanced)
//                .extracting(b -> b.get(Coordinate.of('B', 'I')),
//                        b -> b.get(Coordinate.of('G', 'L')),
//                        b -> b.get(Coordinate.of('O', 'Q')))
//                .allMatch(c -> c instanceof Cell cell && cell.isWallLeft());
//    }
//
//    @ParameterizedTest
//    @MethodSource("orthogonalityParams")
//    void testOrthogonality(String layout, Predicate<Cell> hasWall) {
//        Board testUp = new Board(testMode('A', 'C', layout));
//
//        assertThat(testUp)
//                .extracting(b -> b.get(Coordinate.of('A', 'A')),
//                        b -> b.get(Coordinate.of('A', 'B')),
//                        b -> b.get(Coordinate.of('A', 'C')))
//                .allMatch(c -> c instanceof Cell cell && hasWall.test(cell));
//    }
//
//    public static Stream<Arguments> orthogonalityParams() {
//        return Stream.of(
//                arguments("123", (Predicate<Cell>) Cell::isWallUp),
//                arguments("369", (Predicate<Cell>) Cell::isWallRight),
//                arguments("789", (Predicate<Cell>) Cell::isWallDown),
//                arguments("147", (Predicate<Cell>) Cell::isWallLeft)
//        );
//    }
//
//    private BoardMode testMode(char row, char column, String layout) {
//        return new BoardMode() {
//            @Override
//            public Coordinate getSize() {
//                return Coordinate.of(row, column);
//            }
//
//            @Override
//            public String getLayout() {
//                return layout;
//            }
//        };
//    }
//
//    //
////    @Test
////    void checkMovements() {
////        Board board = new Board(BoardMode.BASIC);
////
////        assertThat(board.canMove('A', 'B', 'A', 'L'))
////                .isTrue()
////    }
//}
