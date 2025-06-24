package magic.mammoth.model;

import lombok.AllArgsConstructor;
import magic.mammoth.model.board.Board;

@AllArgsConstructor
public class Target {
    private Coordinate position;

    public boolean check(Board board) {
        return !board.get(position).isEmpty();
    }
}
