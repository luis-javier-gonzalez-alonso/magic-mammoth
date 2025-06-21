package magic.mammoth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Coordinate {
    private char row;
    private char column;

    public void modify(Direction direction) {
        row += direction.getRowChange();
        column += direction.getColumnChange();
    }

    public int rowValue() {
        return row - 'A';
    }

    public int columnValue() {
        return column - 'A';
    }
}
