package magic.mammoth.model.directions;

public interface Direction {

    static Direction of(int rowChange, int columnChange) {
        return new Direction() {
            @Override
            public int getRowChange() {
                return rowChange;
            }

            @Override
            public int getColumnChange() {
                return columnChange;
            }
        };
    }

    int getRowChange();

    int getColumnChange();

    default Direction opposite() {
        return Direction.of(-getRowChange(), -getColumnChange());
    }

    default boolean equals(Direction other) {
        return this.getRowChange() == other.getRowChange() &&
                this.getColumnChange() == other.getColumnChange();
    }

}
