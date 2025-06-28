package magic.mammoth.application;

import lombok.RequiredArgsConstructor;
import magic.mammoth.application.exceptions.GameIsStarted;
import magic.mammoth.application.exceptions.PlayerForbidden;
import magic.mammoth.game.Game;
import magic.mammoth.game.Player;
import magic.mammoth.game.model.board.BoardMode;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

import static magic.mammoth.game.GameStatus.Completed;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private static final String GAME_KEY = "Game-Key";
    private static final String PLAYER_KEY = "Player-Key";

    private final TaskExecutor gameExecutor;
    private final Map<String, Game> inFlightGames = new ConcurrentHashMap<>();


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

        Player player = new Player(playerName);
        game.addPlayer(player);

        return ok()
                .header(GAME_KEY, gameKey)
                .header(PLAYER_KEY, player.getApiKey())
                .build();
    }

    @GetMapping(value = "/game-details", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Game gameDetails(@RequestHeader(GAME_KEY) String gameKey,
                            @RequestHeader(PLAYER_KEY) String playerKey) {
        Game game = inFlightGames.get(gameKey);

        game.checkPlayer(playerKey);

        return game;
    }

    @GetMapping(value = "/start-game", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void startGame(@RequestHeader(GAME_KEY) String gameKey,
                          @RequestHeader(PLAYER_KEY) String playerKey) {
        Game game = inFlightGames.get(gameKey);

        game.checkPlayer(playerKey);

        gameExecutor.execute(game::start);
    }

    @GetMapping(value = "/game-events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> game(@RequestHeader(GAME_KEY) String gameKey,
                                           @RequestHeader(PLAYER_KEY) String playerKey) {
        Game game = inFlightGames.get(gameKey);

        game.checkPlayer(playerKey);

        if (game.getStatus() == Completed) {
            return noContent().build();
        }

        return ok()
                .body(game.getCommunications().getEventStream(playerKey));
    }

    @ExceptionHandler(PlayerForbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void playerForbidden() {
    }

    @ExceptionHandler(GameIsStarted.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void gameIsStarted() {
    }
}
