package magic.mammoth.model;

public interface CellContent {

    CellContent EMPTY = new CellContent() {
    };

    default boolean isBlocking() {
        return false;
    }

    default boolean canMove() {
        return false;
    }
}