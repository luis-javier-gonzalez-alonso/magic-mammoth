package magic.mammoth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import magic.mammoth.Communications;
import magic.mammoth.events.GameEvent;
import magic.mammoth.events.input.ResolutionAttempt;
import magic.mammoth.events.output.GameStarted;
import magic.mammoth.events.output.NewSceneOfCrime;
import magic.mammoth.events.output.PlayerJoined;
import magic.mammoth.exceptions.GameIsStarted;
import magic.mammoth.exceptions.PlayerForbidden;
import magic.mammoth.model.board.Board;
import magic.mammoth.model.board.BoardMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static java.util.Collections.shuffle;
import static magic.mammoth.model.GameStatus.Created;
import static magic.mammoth.model.GameStatus.Started;

@Data
public class Game {

    private final static Random gameKeyGenerator = new Random(System.nanoTime());

    private final Long gameKey;
    private final Board board;

    private final AtomicReference<GameStatus> status = new AtomicReference<>(Created);

    @JsonIgnore
    private final Map<String, Player> players = new HashMap<>();

    @JsonIgnore
    private final Random sceneOfCrimeGenerator;
    @JsonIgnore
    private List<Character> locationTiles;
    private Coordinate sceneOfCrime;

    @JsonIgnore
    private final Communications communications = new Communications();

    public Game(BoardMode mode) {
        this(gameKeyGenerator.nextLong(0x1000000, 0xFFFFFFF), mode);
    }

    public Game(String gameKey, BoardMode mode) {
        this(Long.parseLong(gameKey, 16), mode);
    }

    private Game(long gameKey, BoardMode mode) {
        this.gameKey = gameKey;
        this.board = new Board(mode);
        this.sceneOfCrimeGenerator = new Random(gameKey);
        // TODO Initial position of meeples
    }

    public String getGameKey() {
        return Long.toHexString(gameKey);
    }

    public GameStatus getStatus() {
        return status.get();
    }

    @JsonProperty("players")
    public List<Player> players() {
        return players.values().stream().toList();
    }

    // Player methods ---------------------------------------------------------

    public void addPlayer(Player player) {
        if (status.get() != Created) {
            throw new GameIsStarted();
        }

        players.put(player.getApiKey(), player);
        broadcastToPlayers(new PlayerJoined(player.getName()));
    }


    public Player getPlayer(String playerKey) {
        checkPlayer(playerKey);
        return players.get(playerKey);
    }

    public void checkPlayer(String playerKey) throws PlayerForbidden {
        if (!players.containsKey(playerKey)) {
            throw new PlayerForbidden();
        }
    }

    // Game logic -------------------------------------------------------------

    public void start() {
        if (!status.compareAndSet(Created, Started)) return;
        broadcastToPlayers(new GameStarted(this));

        try {
            gameLogicLoop();
        } catch (InterruptedException e) {
            // Just finish this game
        }
    }

    private void gameLogicLoop() throws InterruptedException {
        while (!isGameFinished()) {
            Coordinate target;
            do {
                target = newSceneOfCrime();
            } while (!board.cellIsEmpty(target));

            broadcastToPlayers(new NewSceneOfCrime(target));

            GameEvent gameEvent;
            do {
                gameEvent = communications.waitForEvent();
                broadcastToPlayers(gameEvent);
            } while (!(gameEvent instanceof ResolutionAttempt));
        }
    }

    private Coordinate newSceneOfCrime() {
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
        locationTiles = new ArrayList<>(IntStream.range('A', board.getLastRowAndColumn() + 1)
                .mapToObj(i -> (char) i)
                .toList());
        shuffle(locationTiles, sceneOfCrimeGenerator);
    }

    private boolean isGameFinished() {
        return players().stream()
                .anyMatch(p -> p.getSuperTeam().size() >= 6);
    }

    private void handleGameEvent(GameEvent event) {

        if (event instanceof ResolutionAttempt resolutionAttempt) {
            return;
        }

        // If none of those, then ignore this event
    }

    // Communications ---------------------------------------------------------

    private void broadcastToPlayers(GameEvent event) {
        players.keySet()
                .forEach(playerKey -> communications.send(playerKey, event));
    }
}
