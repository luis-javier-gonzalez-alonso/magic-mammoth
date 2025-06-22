package magic.mammoth.model;

import lombok.RequiredArgsConstructor;

import java.util.Set;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class Cell {

    private final Set<CellLimit> limits;

//    private CellContent content;

    public boolean check(CellLimit limit) {
        return limits.contains(limit);
    }

    public String toString() {
        return "C(" + limits.stream()
                .map(Enum::name)
                .collect(joining(",")) + ")";
    }
}