package magic.mammoth.model;

import java.util.List;
import java.util.Map;

public class Board {

    private final Cell[][] cells;

    public Board(Character lastRow, Character lastColumn, String template, Map<Character, List<CellLimit>> legend) {
        int rows = lastRow - 'A' + 1;
        int columns = lastColumn - 'A' + 1;
        this.cells = new Cell[rows][columns];

        for (int row = 0; row < rows; row++) {
            this.cells[row] = new Cell[columns];

            for (int column = 0; column < columns; column++) {
                char key = template.charAt((row * columns) + column);
                System.out.println(key);
                this.cells[row][column] = new Cell(legend.get(key));
            }
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

//    public Cell get(Coordinate coordinate) {
//        return cells[coordinate.rowValue()][coordinate.columnValue()];
//    }

//    public Coordinate resolve(Coordinate origin, Movement movement) {
//        Cell originCell = get(origin);
//        CellContent content = originCell.getContent();
//        if (content.canMove()) {
//            originCell.setContent(null);
//        }
//    }
}
