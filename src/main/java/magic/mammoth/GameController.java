package magic.mammoth;

import magic.mammoth.model.Game;
import magic.mammoth.model.GeneratedKey;
import magic.mammoth.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api")
public class GameController {

    private static final String GAME_KEY = "Game-Key";
    private static final String PLAYER_KEY = "Player-Key";

    private final Map<GeneratedKey, Game> inFlightGames = new ConcurrentHashMap<>();

    // Create game (gives game key, this is then mandatory everywhere)
    //// POST /create-game -> { "gameKey": "Random unique (in flight) [a-z0-9]{6}" }
    // Join game
    // Start game -> for every joined player, send them updates of Coordinates for target
    // Provide solution -> triggers 30 seconds for the other players to answer + regular time updates
    // Wait for resolution (1 min max)

    @PostMapping(value = "/create-game", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGame() {
        GeneratedKey gameKey = new GeneratedKey();
        inFlightGames.put(gameKey, new Game());

        return created(URI.create("/api/join-game/" + gameKey))
                .header(GAME_KEY, gameKey.toString()).build();
    }

    @PostMapping(value = "/join-game", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> joinGame(@RequestHeader(GAME_KEY) GeneratedKey gameKey,
                                         @RequestParam("player") String playerName) {
        final Player player = new Player(playerName);
        inFlightGames.get(gameKey).getPlayers().add(player);

        return created(URI.create("/api/board/" + gameKey))
                .header(GAME_KEY, gameKey.toString())
                .header(PLAYER_KEY, player.getApiKey().toString()).build();
    }

    @PostMapping(value = "/game-details", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Game gameDetails(@RequestHeader(GAME_KEY) GeneratedKey gameKey,
                            @RequestHeader(PLAYER_KEY) GeneratedKey playerKey) {
        Game game = inFlightGames.get(gameKey);

        game.getPlayers().stream()
                .filter(player -> playerKey.equals(player.getApiKey()))
                .findFirst()
                .orElseThrow();

        return game;
    }
}
