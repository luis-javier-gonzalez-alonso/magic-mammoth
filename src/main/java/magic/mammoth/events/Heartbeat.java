package magic.mammoth.events;

public class Heartbeat implements GameEvent {
    @Override
    public String getEventName() {
        return "heartbeat";
    }
}
