package magic.mammoth.model.board;

import lombok.Getter;
import magic.mammoth.model.Coordinate;

import java.util.Map;
import java.util.Set;

public class Board {

    @Getter
    private final Character lastRow;
    @Getter
    private final Character lastColumn;
    private final Cell[][] cells;

    public Board(Character lastRow, Character lastColumn, String template, Map<Character, Set<CellLimit>> legend) {
        this.lastRow = lastRow;
        this.lastColumn = lastColumn;
        int rows = lastRow - 'A' + 1;
        int columns = lastColumn - 'A' + 1;
        this.cells = new Cell[rows][columns];

        for (int row = 0; row < rows; row++) {
            this.cells[row] = new Cell[columns];

            for (int column = 0; column < columns; column++) {
                this.cells[row][column] = new Cell(legend
                        .getOrDefault(template.charAt((row * columns) + column), Set.of()));
            }
        }
    }

    public boolean outOfBounds(Coordinate coordinate) {
        return get(coordinate) != null;
    }

    public boolean cellIsEmpty(Coordinate coordinate) {
        return get(coordinate).isEmpty();
    }

    public Cell get(Coordinate coordinate) {
        try {
            return cells[coordinate.rowValue()][coordinate.columnValue()];

        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void print() {
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                System.out.print(cells[row][column]);
            }
            System.out.println();
        }
    }
}
