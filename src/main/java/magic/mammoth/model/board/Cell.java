package magic.mammoth.model.board;

import lombok.RequiredArgsConstructor;
import magic.mammoth.model.directions.Direction;
import magic.mammoth.model.meeples.MutantMeeple;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class Cell {

    private final Set<CellLimit> limits;

    private MutantMeeple content;

    public boolean isEmpty() {
        return content == null;
    }

    public boolean check(CellLimit limit) {
        return limits.contains(limit);
    }

    public boolean checkAll(Predicate<CellLimit> limitTrait) {
        return limits.stream().allMatch(limitTrait);
    }

    public boolean checkAny(Predicate<CellLimit> limitTrait) {
        return limits.stream().anyMatch(limitTrait);
    }

    public boolean checkAny(Direction direction) {
        return limits.stream().anyMatch(l -> l.getDirection().equals(direction));
    }

    public boolean checkAll(CellLimit... limits) {
        return checkAll(asList(limits));
    }

    public boolean checkAll(List<CellLimit> limits) {
        return this.limits.containsAll(limits);
    }

    public boolean checkAny(CellLimit... limits) {
        return checkAny(asList(limits));
    }

    public boolean checkAny(List<CellLimit> limits) {
        for (CellLimit limit : limits) {
            if (check(limit)) {
                return true;
            }
        }
        return false;
    }

//    Map<Direction, Predicate<Cell>> checks = Map.of(
//            DiagonalDirections.UpLeft, _ -> true,
//            OrthogonalDirections.Up, _ -> checkAny(CellLimit::isTop),
//            DiagonalDirections.UpRight, _ -> true,
//            OrthogonalDirections.Left, _ -> checkAny(CellLimit::isLeft),
//            Direction.of(0, 0), _ -> false,
//            OrthogonalDirections.Right, _ -> checkAny(CellLimit::isRight),
//            DiagonalDirections.DownLeft, _ -> true,
//            OrthogonalDirections.Down, _ -> checkAny(CellLimit::isBottom),
//            DiagonalDirections.DownRight, _ -> true);
//
//    public boolean check(Direction direction, Cell next) {
//        return checks.get(direction).get();
//    }
//
//    public boolean to(Direction direction) {
//        return checks.get(direction).get();
//
////        return switch (direction) {
////            case Up -> !limits.contains(CellLimit.BoardTop) && !limits.contains(CellLimit.WallTop);
////            case Right -> !limits.contains(CellLimit.BoardRight) && !limits.contains(CellLimit.WallRight);
////            case Down -> !limits.contains(CellLimit.BoardBottom) && !limits.contains(CellLimit.WallBottom);
////            case Left -> !limits.contains(CellLimit.BoardLeft) && !limits.contains(CellLimit.WallLeft);
////        };
//    }
//
//    public boolean from(Direction direction) {
//        return checks.get(direction.opposite()).get()
////        return switch (direction) {
////            case Down -> !limits.contains(CellLimit.WallTop);
////            case Left -> !limits.contains(CellLimit.WallRight);
////            case Up -> !limits.contains(CellLimit.WallBottom);
////            case Right -> !limits.contains(CellLimit.WallLeft);
////        };
//    }

    public String toString() {
        return "C(" + limits.stream()
                .map(Enum::name)
                .collect(joining(",")) + ")";
    }
}