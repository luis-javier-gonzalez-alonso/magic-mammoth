package magic.mammoth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
    Up(-1, 0),
    Right(0, 1),
    Down(1, 0),
    Left(0, -1);

    private int rowChange;
    private int columnChange;
}
