package magic.mammoth.game.events;

import lombok.Setter;

public abstract class GameEvent {

    @Setter
    private Long timestamp;

    public long getTimestamp() {
        if (timestamp == null) {
            timestamp = System.nanoTime();
        }
        return timestamp;
    }

    //    @JsonIgnore
    public abstract String getEventName();
}
