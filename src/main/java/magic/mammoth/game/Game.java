package magic.mammoth.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import magic.mammoth.application.exceptions.GameIsStarted;
import magic.mammoth.application.exceptions.PlayerForbidden;
import magic.mammoth.game.events.GameEvent;
import magic.mammoth.game.events.input.ResolutionAttempt;
import magic.mammoth.game.events.input.ResolutionDemonstration;
import magic.mammoth.game.events.output.GameStarted;
import magic.mammoth.game.events.output.NewSceneOfCrime;
import magic.mammoth.game.events.output.PlayerJoined;
import magic.mammoth.game.model.Coordinate;
import magic.mammoth.game.model.board.Board;
import magic.mammoth.game.model.board.BoardMode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static java.time.Duration.between;
import static java.time.Duration.ofMillis;
import static java.time.Instant.now;
import static java.util.Collections.shuffle;
import static magic.mammoth.game.GameStatus.Completed;
import static magic.mammoth.game.GameStatus.Created;
import static magic.mammoth.game.GameStatus.Failed;
import static magic.mammoth.game.GameStatus.Started;

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
    //    @JsonIgnore
    private List<Character> locationTiles;
    private Coordinate sceneOfCrime;

    @JsonIgnore
    private final Communications communications = new Communications();
    private final GameConfiguration configuration = GameConfiguration.builder().build();

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
            status.set(Completed);

        } catch (InterruptedException e) {
            status.set(Failed);
        }
    }

    private void gameLogicLoop() throws InterruptedException {
        Coordinate target;
        GameEvent gameEvent;

        while (!isGameFinished()) {

            // new crime target
            do {
                target = newSceneOfCrime();
            } while (!board.cellIsEmpty(target));

            broadcastToPlayers(new NewSceneOfCrime(target));

            // wait for resolutions
            do {
                gameEvent = communications.waitForEvent();
                broadcastToPlayers(gameEvent);
                handleGameEvent(gameEvent); // TODO each of this has a different set of allowed events
            } while (!(gameEvent instanceof ResolutionAttempt));

            // wait additional time
            Instant start = now();
            do {
                gameEvent = communications.waitForEvent(ofMillis(100));
                if (gameEvent != null) {
                    broadcastToPlayers(gameEvent);
                    handleGameEvent(gameEvent); // TODO each of this has a different set of allowed events
                }
            } while (between(start, now()).minus(configuration.getAdditionalTime()).isNegative());

            // for each player that provided a solution attempt in time
            {
                // wait for demonstration
                do {
                    gameEvent = communications.waitForEvent(ofMillis(100));
                    if (gameEvent != null) {
                        broadcastToPlayers(gameEvent);
                        handleGameEvent(gameEvent); // TODO each of this has a different set of allowed events
                    }
                } while (!(gameEvent instanceof ResolutionDemonstration demo)
                        && (between(start, now()).minus(configuration.getResolutionDemonstration()).isNegative()));

                // update player superTeam if demo is successful
                // modify meeple positions if demo is successful
                // break for if demo is successful
            }
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
