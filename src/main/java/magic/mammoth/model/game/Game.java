package magic.mammoth.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import magic.mammoth.events.GameEvent;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.board.Board;
import magic.mammoth.model.board.BoardMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Collections.shuffle;

@Data
public class Game {

    private final static Random gameKeyGenerator = new Random(System.nanoTime());

    private final Long gameKey;
    private final Board board;

    @JsonIgnore
    private final Map<String, Player> players = new HashMap<>();

    @JsonIgnore
    private final Random sceneOfCrimeGenerator;
    @JsonIgnore
    private List<Character> locationTiles;
    private Coordinate sceneOfCrime;

    public Game(BoardMode mode) {
        this.gameKey = gameKeyGenerator.nextLong(0x100000, 0xFFFFFF); // key size is six hex digits
        this.board = new Board(mode);
        this.sceneOfCrimeGenerator = new Random(gameKey);
        // TODO Initial position of meeples
    }

    public String getGameKey() {
        return Long.toHexString(gameKey);
    }

    @JsonProperty("players")
    public List<Player> publicPlayers() {
        return players.values().stream().toList();
    }

    public void broadcastToPlayers(GameEvent event) {
        players.values()
                .parallelStream() // TODO Is this fair enough?
                .forEach(p -> p.send(event));
    }

    public Coordinate newSceneOfCrime() {
        sceneOfCrime = Coordinate.of(turnOverLocationTile(), turnOverLocationTile());
        return sceneOfCrime;
    }

    private Character turnOverLocationTile() {
        if (locationTiles == null || locationTiles.isEmpty()) {
            resetLocationTiles();
        }
        return locationTiles.removeLast();
    }

    private void resetLocationTiles() {
        locationTiles = IntStream.range('A', board.getLastRowAndColumn() + 1)
                .mapToObj(i -> (char) i)
                .toList();
        shuffle(locationTiles, sceneOfCrimeGenerator);
    }
}
