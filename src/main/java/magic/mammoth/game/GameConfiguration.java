package magic.mammoth.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import magic.mammoth.game.model.board.BoardMode;
import magic.mammoth.game.model.meeples.BlueBeamer;
import magic.mammoth.game.model.meeples.Carbon;
import magic.mammoth.game.model.meeples.ForrestJump;
import magic.mammoth.game.model.meeples.McEdge;
import magic.mammoth.game.model.meeples.MutantMeeple;
import magic.mammoth.game.model.meeples.OzzyMosis;
import magic.mammoth.game.model.meeples.Shortstop;
import magic.mammoth.game.model.meeples.Sidestep;
import magic.mammoth.game.model.meeples.Skewt;

import java.time.Duration;
import java.util.List;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class GameConfiguration {

    @Builder.Default
    BoardMode boardMode = BoardMode.BASIC;

    @Builder.Default
    Duration additionalTime = Duration.ofSeconds(30);

    @Builder.Default
    Duration resolutionDemonstration = Duration.ofSeconds(60);

    @Builder.Default
    List<MutantMeeple> participantMeeples = List.of(
            new ForrestJump(), new OzzyMosis(), new BlueBeamer(), new McEdge(),
            new Shortstop(), new Sidestep(), new Skewt(), new Carbon());
}