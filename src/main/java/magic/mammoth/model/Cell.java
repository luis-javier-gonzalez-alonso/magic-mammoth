package magic.mammoth.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static java.util.stream.Collectors.joining;

@Data
@RequiredArgsConstructor
public class Cell {

    private final Set<CellLimit> limits;

//    private CellContent content;

    public boolean to(Direction direction) {
        return switch (direction) {
            case Up -> !limits.contains(CellLimit.BoardTop) && !limits.contains(CellLimit.WallTop);
            case Right -> !limits.contains(CellLimit.BoardRight) && !limits.contains(CellLimit.WallRight);
            case Down -> !limits.contains(CellLimit.BoardBottom) && !limits.contains(CellLimit.WallBottom);
            case Left -> !limits.contains(CellLimit.BoardLeft) && !limits.contains(CellLimit.WallLeft);
        };
    }

    public boolean from(Direction direction) {
        return switch (direction) {
            case Down -> !limits.contains(CellLimit.WallTop);
            case Left -> !limits.contains(CellLimit.WallRight);
            case Up -> !limits.contains(CellLimit.WallBottom);
            case Right -> !limits.contains(CellLimit.WallLeft);
        };
    }

    public String toString() {
        return "C(" + limits.stream()
                .map(Enum::name)
                .collect(joining(",")) + ")";
    }
}