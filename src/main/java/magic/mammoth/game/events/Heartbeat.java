package magic.mammoth.game.events;

public class Heartbeat extends GameEvent {

    @Override
    public String getEventName() {
        return "heartbeat";
    }
}
