package magic.mammoth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    public int rowValue() {
        return row - 'A';
    }

    public int columnValue() {
        return column - 'A';
    }
}
