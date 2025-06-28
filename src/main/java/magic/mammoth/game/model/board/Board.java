package magic.mammoth.game.model.board;

import lombok.Getter;
import magic.mammoth.game.model.Coordinate;

import java.util.Map;
import java.util.Set;

public class Board {

    @Getter
    private final Character lastRowAndColumn; // board is a square
    private final Cell[][] cells;

    public Board(BoardMode mode) {
        this(mode.getLastRowAndColumn(), mode.getTemplate(), mode.getLegend());
    }

    public Board(Character lastRowAndColumn, String template, Map<Character, Set<CellLimit>> legend) {
        this.lastRowAndColumn = lastRowAndColumn;
        int size = lastRowAndColumn - 'A' + 1;
        this.cells = new Cell[size][size];

        for (int row = 0; row < size; row++) {
            this.cells[row] = new Cell[size];

            for (int column = 0; column < size; column++) {
                this.cells[row][column] = new Cell(legend
                        .getOrDefault(template.charAt((row * size) + column), Set.of()));
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
        System.out.print(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                sb.append(cells[row][column]);
            }
            sb.append("/n");
        }
        return sb.toString();
    }
}
