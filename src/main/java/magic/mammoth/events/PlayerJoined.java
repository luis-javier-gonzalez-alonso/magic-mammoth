package magic.mammoth.events;

public record PlayerJoined(String playerName) implements GameEvent {

    @Override
    public String getEventName() {
        return "player-joined";
    }
}
