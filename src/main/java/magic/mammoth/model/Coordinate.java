//package magic.mammoth.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor(staticName = "of")
//public class Coordinate {
//
//    private BoardMode boardMode;
//    private char row;
//    private char column;
//
//    public int rowValue() {
//        int i = row - boardMode.ge;
//        System.out.println("row value -> " + i);
//        return i;
//    }
//
//    public int columnValue() {
//        int i = column - Coordinate.start;
//        System.out.println("col value -> " + i);
//        return i;
//    }
//
//    public Coordinate up() {
//        return row == start ? this : Coordinate.of((char) (row - 1), column);
//    }
//
//    public Coordinate right() {
//        return row == end ? this : Coordinate.of(row, (char) (column + 1));
//    }
//
//    public Coordinate down() {
//        return row == end ? this : Coordinate.of((char) (row + 1), column);
//    }
//
//    public Coordinate left() {
//        return row == start ? this : Coordinate.of(row, (char) (column - 1));
//    }
//}
