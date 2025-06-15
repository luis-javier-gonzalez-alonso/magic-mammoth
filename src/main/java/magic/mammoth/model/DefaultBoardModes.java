package magic.mammoth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

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
public enum DefaultBoards {

    BASIC(Coordinate.of('R', 'R'), """
            X00004000000040000
            000700000300000000
            004000004000000200
            000002000001000402
            040000302000020000
            000005000000000090
            007000000020400000
            000070020090002000
            200400000000004000
            000000700000200040
            002000600200000400
            030000680002000000
            000200000400000042
            000090040000020000
            200000000040002090
            010000007000200000
            000007000000004000
            00400000400040020X
            """.replace("\n", "")),

    ADVANCED(Coordinate.of('R', 'R'), """
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
            """.replace("\n", ""));

    private char lastRow;
    private char lastColumn;
    private String template;
    private Map<Character, List<CellLimit>> legend;
}
