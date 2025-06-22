package magic.mammoth.model.game;

import lombok.Data;
import lombok.NoArgsConstructor;
import magic.mammoth.model.board.Board;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Game {

    private String gameKey;

    private Board board;

    private List<Player> players = new ArrayList<>();

    private Statistics statistics;
}
