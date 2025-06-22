package magic.mammoth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import magic.mammoth.model.directions.Direction;

@Data
@AllArgsConstructor(staticName = "of")
public class Coordinate {

    private char row;
    private char column;

    public static Coordinate copyOf(Coordinate origin) {
        return Coordinate.of(origin.row, origin.column);
    }

    public Coordinate set(char row, char column) {
        this.row = row;
        this.column = column;
        return this;
    }

    public Coordinate modify(int rowModifier, int columnModifier) {
        row += rowModifier;
        column += columnModifier;
        return this;
    }

    public Coordinate modify(Direction direction) {
        row += direction.getRowChange();
        column += direction.getColumnChange();
        return this;
    }

    public int rowValue() {
        return row - 'A';
    }

    public int columnValue() {
        return column - 'A';
    }
}
