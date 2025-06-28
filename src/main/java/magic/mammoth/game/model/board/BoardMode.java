package magic.mammoth.game.model.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 1 2 3
 * 4   6
 * 7 8 9
 *
 * Walls:
 * 2,4,6,8 -> single wall on that side of the cell
 * 1,3,7,9 -> corner walls at both sides of that corner
 */

@Getter
@AllArgsConstructor
public enum BoardMode {

    BASIC('R', """
            .haaaiaaaaaaaiaaab
            h..7.....3.......c
            g.4.....4......2.c
            g....2.....1...4.j
            g4....3.2....2...c
            g....5..........9c
            g.7.......2.4....c
            g...7..2..9...2..c
            m..4..........4..c
            g.....7.....2...4c
            g.2...6..2.....4.c
            g3....68...2.....c
            g..2.....4......4j
            g...9..4.....2...c
            m.........4...2.9c
            g1......7...2....c
            g....7........4..d
            feleeeeeleeeleekd.
            """.replace("\n", ""),
            merge(
                    Map.of(
                            'a', Set.of(CellLimit.BoardTop),
                            'b', Set.of(CellLimit.BoardTop, CellLimit.BoardRight),
                            'c', Set.of(CellLimit.BoardRight),
                            'd', Set.of(CellLimit.BoardRight, CellLimit.BoardBottom),
                            'e', Set.of(CellLimit.BoardBottom),
                            'f', Set.of(CellLimit.BoardBottom, CellLimit.BoardLeft),
                            'g', Set.of(CellLimit.BoardLeft),
                            'h', Set.of(CellLimit.BoardLeft, CellLimit.BoardTop)),
                    Map.of(
                            'i', Set.of(CellLimit.BoardTop, CellLimit.WallLeft),
                            'j', Set.of(CellLimit.BoardRight, CellLimit.WallTop),
                            'k', Set.of(CellLimit.BoardBottom, CellLimit.WallTop),
                            'l', Set.of(CellLimit.BoardBottom, CellLimit.WallLeft),
                            'm', Set.of(CellLimit.BoardLeft, CellLimit.WallTop)),
                    Map.of(
                            '1', Set.of(CellLimit.WallLeft, CellLimit.WallTop),
                            '2', Set.of(CellLimit.WallTop),
                            '3', Set.of(CellLimit.WallTop, CellLimit.WallRight),
                            '4', Set.of(CellLimit.WallLeft),
                            '6', Set.of(CellLimit.WallRight),
                            '7', Set.of(CellLimit.WallLeft, CellLimit.WallBottom),
                            '8', Set.of(CellLimit.WallBottom),
                            '9', Set.of(CellLimit.WallBottom, CellLimit.WallRight))
            )),

    ADVANCED('R', """
            X00000000000400000
            000000204000000400
            002000000000004000
            000000000040000002
            000002000000040000
            020000400200000000
            000000000004002000
            000200004000000000
            000004000000000020
            000000000000420000
            002000400020000000
            000040002000000200
            200000000040000002
            000000040000200000
            000020000000000040
            040000020000004000
            000400000002000000
            00000040000000000X
            """.replace("\n", ""),
            Map.of());

    private char lastRowAndColumn;
    private String template;
    private Map<Character, Set<CellLimit>> legend;

    private static Map<Character, Set<CellLimit>> merge(Map<Character, Set<CellLimit>>... items) {
        Map<Character, Set<CellLimit>> aggregate = new HashMap<>();
        for (var map : items) {
            aggregate.putAll(map);
        }
        return aggregate;
    }

    public List<Character> getInitialPositions() {
        return List.of('C', 'E', 'G', 'I', 'J', 'L', 'N', 'P');
    }
}
