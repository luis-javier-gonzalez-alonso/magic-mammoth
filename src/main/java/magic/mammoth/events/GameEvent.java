package magic.mammoth.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface GameEvent {

    @JsonIgnore
    String getEventName();
}
