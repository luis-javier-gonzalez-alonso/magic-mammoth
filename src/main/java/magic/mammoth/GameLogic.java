package magic.mammoth;

import magic.mammoth.events.NewSceneOfCrime;
import magic.mammoth.model.Coordinate;
import magic.mammoth.model.game.Game;

public class GameLogic {

    public void gameInitiation() {
        // Create board
        // Position meeples in initial positions
        // initialise statistics
    }

    public void gameLoop(Game game) {
        // While no player has 6 meeples in its team
        roundLoop(game);
    }

    public void roundLoop(Game game) {
        {
            // 1. New random target
            Coordinate target;
            do {
                target = game.newSceneOfCrime();
            } while (!game.getBoard().cellIsEmpty(target));

            game.broadcastToPlayers(new NewSceneOfCrime(target));

            // 2. Await first resolution + 30 seconds or surrender of all players
            // -> Add option to declare that you can't find a solution, and skip if all agree
            // -> Add option to avoid waiting the 30 additional seconds, and skip if all (other than the solver) agree

            // 2.a. Wait for resolution of player with lower solution steps number
            // -> Add option to skip a player resolution (if it was a bluff or cannot remember solution)
            // 2.b. If no resolution, generate a new target (1)

            // 3.a if solution is correct, add final meeple to player's team
            // 3.b if solution is incorrect or player skip resolution, that player should choose a meeple from his team to let go (if any).
        }
    }
}
