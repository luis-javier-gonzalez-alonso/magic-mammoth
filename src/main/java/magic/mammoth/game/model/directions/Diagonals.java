package magic.mammoth.game.model.directions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Diagonals implements Direction {

    UpLeft(Orthogonals.Up, Orthogonals.Left),
    UpRight(Orthogonals.Up, Orthogonals.Right),
    DownLeft(Orthogonals.Down, Orthogonals.Left),
    DownRight(Orthogonals.Down, Orthogonals.Right);

    private Orthogonals a;
    private Orthogonals b;

    @Override
    public int getRowChange() {
        return a.getRowChange() + b.getRowChange();
    }

    @Override
    public int getColumnChange() {
        return a.getColumnChange() + b.getColumnChange();
    }
}
