package magic.mammoth.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {
    private final List<Player> players = new ArrayList<>();
}
