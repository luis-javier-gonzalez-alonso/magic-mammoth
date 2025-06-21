package magic.mammoth.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Piece {

    private String name;
    private Coordinate position;

    public Coordinate calculateDestination(Board board, Direction direction) {
        Coordinate current = Coordinate.of(position.getRow(), position.getColumn());
        while (board.canMove(current, direction)) {
            current.modify(direction);
        }
        return current;
    }

    public void move(Board board, Direction direction) {
        position = calculateDestination(board, direction);
        log.trace("[{}] Arrived to [{},{}]", name, position.getRow(), position.getColumn());
    }
}
