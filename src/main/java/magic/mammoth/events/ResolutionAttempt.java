package magic.mammoth.events;

public record ResolutionAttempt(int movements) implements GameEvent {

    @Override
    public String getEventName() {
        return "resolution-attempt";
    }
}
