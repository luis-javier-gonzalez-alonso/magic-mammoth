package magic.mammoth.game.model.meeples;

import magic.mammoth.game.model.movements.Movement;

import java.util.Set;

public class Carbon extends MutantMeeple {

    @Override
    public String name() {
        return "Carbon (black)";
    }

    @Override
    protected Movement power() {
        // TODO needs some additional scaffolding to be able to get available powers from game.
        return (game, origin) -> Set.of();
    }
}
