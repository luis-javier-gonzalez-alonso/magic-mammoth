package magic.mammoth.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CellLimit {

    BoardTop(false, true, false, false, false),
    BoardRight(false, false, true, false, false),
    BoardBottom(false, false, false, true, false),
    BoardLeft(false, false, false, false, true),

    WallTop(true, true, false, false, false),
    WallRight(true, false, true, false, false),
    WallBottom(true, false, false, true, false),
    WallLeft(true, false, false, false, true);


    boolean isWall;

    boolean top;
    boolean right;
    boolean bottom;
    boolean left;
}
