package magic.mammoth.game.events;

public class Heartbeat implements GameEvent {
    @Override
    public String getEventName() {
        return "heartbeat";
    }
}
