package magic.mammoth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import magic.mammoth.model.meeples.MutantMeeple;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Player {

    @JsonIgnore
    private final String apiKey = UUID.randomUUID().toString();

    private final String name;

    /**
     * When a player gets to the target with a meeple,
     * that meeple is added to the team of the player.
     * <p>
     * A player cannot use a meeple that is already in its team.
     */
    private final Set<MutantMeeple> superTeam = new HashSet<>();

}
