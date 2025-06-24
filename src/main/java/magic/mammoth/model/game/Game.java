package magic.mammoth.model.game;

import lombok.Data;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.Target;
import magic.mammoth.model.board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Long.parseLong;

@Data
public class Game {

    private final String gameKey;

    private Board board;
    private Target target;

    private List<Player> players = new ArrayList<>();

    private Statistics statistics;

    // Technical
    private List<Character> poolForTarget;
    private final Random random;

    public Game(String gameKey) {
        this.gameKey = gameKey;
        this.random = new Random(parseLong(gameKey, 16));
    }

    public void newTarget() {
        do {
            List<Character> values = random.ints(2, 'A', board.getLastColumn())
                    .mapToObj(i -> (char) i)
                    .toList();
            target = new Target(Coordinate.of(values.get(0), values.get(1)));
        } while (target.check(board)); // resolved -> generate a new one
    }
}
