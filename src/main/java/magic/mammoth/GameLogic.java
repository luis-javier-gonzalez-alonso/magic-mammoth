package magic.mammoth;

public class GameLogic {

    public void gameInitiation() {
        // Create board
        // Position meeples in initial positions
        // initialise statistics
    }

    public void gameLoop() {
        // While no player has 6 meeples in its team
        roundLoop();
    }

    public void roundLoop() {
        {
            // 1. New random target
            newTarget();
            // 2. Await first resolution + 30 seconds or surrender of all players
            // -> Add option to declare that you can't find a solution, and skip if all agree
            // -> Add option to avoid waiting the 30 additional seconds, and skip if all (other than the solver) agree
            // 2.a. Wait for resolution of player with lower solution steps number
            // -> Add option to skip a player resolution (if it was a bluff or cannot remember solution)
            // 2.b. If no resolution, generate a new target (1)
            // 3.a if solution is correct, add final meeple to player's team
            // 3.b if solution is incorrect or player skept resolution, that player should choose a meeple from his team to let go.
        }
    }

    public void newTarget() {

    }
}
