package magic.mammoth;

import magic.mammoth.events.PlayerJoined;
import magic.mammoth.model.board.BoardMode;
import magic.mammoth.model.game.Game;
import magic.mammoth.model.game.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class GameController {

    private static final String GAME_KEY = "Game-Key";
    private static final String PLAYER_KEY = "Player-Key";

    private final Map<String, Game> inFlightGames = new ConcurrentHashMap<>();

    // Create game (gives game key, this is then mandatory everywhere)
    //// POST /create-game -> { "gameKey": "Random unique (in flight) [a-z0-9]{6}" }
    // Join game
    // Start game -> for every joined player, send them updates of Coordinates for target
    // Provide solution -> triggers 30 seconds for the other players to answer + regular time updates
    // Wait for resolution (1 min max)

    @PostMapping(value = "/create-game", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGame(@RequestParam(required = false, defaultValue = "BASIC") BoardMode mode) {
        Game game = new Game(mode);
        inFlightGames.put(game.getGameKey(), game);

        return created(URI.create("/api/join-game/" + game.getGameKey()))
                .header(GAME_KEY, game.getGameKey()).build();
    }

    @PostMapping(value = "/join-game/{gameKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> joinGame(@PathVariable("gameKey") String gameKey,
                                         @RequestParam("player") String playerName) {
        Game game = inFlightGames.get(gameKey);

        final Player player = new Player(playerName);
        game.getPlayers()
                .put(player.getApiKey(), player);

        game.broadcastToPlayers(new PlayerJoined(playerName));

        return created(URI.create("/game"))
                .header(GAME_KEY, gameKey)
                .header(PLAYER_KEY, player.getApiKey()).build();
    }

    @PostMapping(value = "/game-details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> gameDetails(@RequestHeader(GAME_KEY) String gameKey,
                                            @RequestHeader(PLAYER_KEY) String playerKey) {
        Game game = inFlightGames.get(gameKey);

        if (!game.getPlayers().containsKey(playerKey)) {
            return ResponseEntity.status(403).build();
        }

        return ok(game);
    }

    @GetMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter game(@RequestHeader(GAME_KEY) String gameKey,
                           @RequestHeader(PLAYER_KEY) String playerKey) {

        return inFlightGames.get(gameKey)
                .getPlayers()
                .get(playerKey)
                .getEmitter();
    }
}
