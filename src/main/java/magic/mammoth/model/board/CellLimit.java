package magic.mammoth.model.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import magic.mammoth.model.directions.Direction;
import magic.mammoth.model.directions.Orthogonals;

@Getter
@AllArgsConstructor
public enum CellLimit {

    BoardTop(Orthogonals.Up, true),
    BoardRight(Orthogonals.Right, true),
    BoardBottom(Orthogonals.Down, true),
    BoardLeft(Orthogonals.Left, true),

    WallTop(Orthogonals.Up, false),
    WallRight(Orthogonals.Right, false),
    WallBottom(Orthogonals.Down, false),
    WallLeft(Orthogonals.Left, false);


    Orthogonals direction;
    boolean border;

    public boolean is(Direction direction) {
        return this.direction.equals(direction);
    }
}
