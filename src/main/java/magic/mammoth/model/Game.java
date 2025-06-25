package magic.mammoth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import magic.mammoth.events.GameEvent;
import magic.mammoth.events.NewSceneOfCrime;
import magic.mammoth.events.ResolutionAttempt;
import magic.mammoth.model.board.Board;
import magic.mammoth.model.board.BoardMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Collections.shuffle;
import static magic.mammoth.model.GameStatus.Created;
import static magic.mammoth.model.GameStatus.Started;

@Data
public class Game {

    private final static Random gameKeyGenerator = new Random(System.nanoTime());

    private final Long gameKey;
    private final Board board;

    @Setter(AccessLevel.NONE)
    private GameStatus status = Created;

    @JsonIgnore
    private final Map<String, Player> players = new HashMap<>();

    @JsonIgnore
    private final Random sceneOfCrimeGenerator;
    @JsonIgnore
    private List<Character> locationTiles;
    private Coordinate sceneOfCrime;

    public Game(BoardMode mode) {
        this(gameKeyGenerator.nextLong(0x1000000, 0xFFFFFFF), mode);
    }

    public Game(String gameKey, BoardMode mode) {
        this(Long.parseLong(gameKey, 16), mode);
    }

    public Game(long gameKey, BoardMode mode) {
        this.gameKey = gameKey;
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

    // Game communications
    GameEvent receivedEvent;

    // TODO replace with ReentrantLock
    private synchronized GameEvent waitForEvent() {
        while (receivedEvent == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                // just check while condition
            }
        }
        notifyAll();
        GameEvent received = this.receivedEvent;
        receivedEvent = null;
        return received;
    }

    // TODO replace with ReentrantLock
    public synchronized void submitEvent(GameEvent event) {
        while (receivedEvent != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                // just check while condition
            }
        }
        this.receivedEvent = event;
        notifyAll();
    }

    //TODO make private, use submit event instead
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
        locationTiles = new ArrayList<>(IntStream.range('A', board.getLastRowAndColumn() + 1)
                .mapToObj(i -> (char) i)
                .toList());
        shuffle(locationTiles, sceneOfCrimeGenerator);
    }

    public void start() {
        status = Started;

        while (!isGameFinished()) {
            Coordinate target;
            do {
                target = newSceneOfCrime();
            } while (!board.cellIsEmpty(target));

            broadcastToPlayers(new NewSceneOfCrime(target));

            GameEvent gameEvent;
            do {
                gameEvent = waitForEvent();
                broadcastToPlayers(gameEvent);
            } while (!(gameEvent instanceof ResolutionAttempt));
        }
    }

    private boolean isGameFinished() {
        return publicPlayers().stream()
                .anyMatch(p -> p.getSuperTeam().size() >= 6);
    }
}
