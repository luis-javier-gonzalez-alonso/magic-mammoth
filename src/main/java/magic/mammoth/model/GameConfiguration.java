package magic.mammoth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
@AllArgsConstructor
public class GameConfiguration {

    @Builder.Default
    Duration additionalTime = Duration.ofSeconds(30);

    @Builder.Default
    Duration resolutionDemonstration = Duration.ofSeconds(30);
}