package magic.mammoth.model;

import java.util.Map;
import java.util.Set;

public class Board {

    private final Cell[][] cells;

    public Board(Character lastRow, Character lastColumn, String template, Map<Character, Set<CellLimit>> legend) {
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

    public boolean canMove(Coordinate origin, Direction direction) {
        Cell cellOrigin = cells[origin.rowValue()][origin.columnValue()];
        int rowDestination = (origin.rowValue() + direction.getRowChange() + cells.length) % cells.length;
        int columnDestination = (origin.columnValue() + direction.getColumnChange() + cells[0].length) % cells[0].length;
        Cell cellDestination = cells[rowDestination][columnDestination];

        return cellOrigin.to(direction) && cellDestination.from(direction);
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
