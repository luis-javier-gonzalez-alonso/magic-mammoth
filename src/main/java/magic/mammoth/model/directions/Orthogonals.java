package magic.mammoth.model.directions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Orthogonals implements Direction {

    Up(-1, 0),
    Right(0, 1),
    Down(1, 0),
    Left(0, -1);

    private int rowChange;
    private int columnChange;
}
