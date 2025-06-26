package magic.mammoth;

import lombok.SneakyThrows;
import magic.mammoth.events.GameEvent;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

public class Communications {

    // Game communications ----------------------------------------------------

    // Fair keeps order of lock
    private final ReentrantLock inputCommunication = new ReentrantLock(true);
    private final BlockingQueue<GameEvent> communications = new LinkedBlockingQueue<>();

    public void submitEvent(GameEvent event) {
        inputCommunication.lock();

        try {
            communications.add(event);

        } finally {
            inputCommunication.unlock();
        }
    }

    public GameEvent waitForEvent() throws InterruptedException {
        return communications.take();
    }

    // Player communications --------------------------------------------------

    private final Map<String, SseEmitter> playerEventStreams = new ConcurrentHashMap<>();

    public SseEmitter getEventStream(String playerKey) {
        return playerEventStreams.computeIfAbsent(playerKey, this::createEmitter);
    }

    @SneakyThrows
    public void send(String playerKey, GameEvent event) {
        try {
            playerEventStreams.computeIfAbsent(playerKey, this::createEmitter)
                    .send(event().name(event.getEventName()).data(event));

        } catch (IllegalStateException exception) {
            // just retry with a new emitter
            playerEventStreams.remove(playerKey);
            playerEventStreams.computeIfAbsent(playerKey, this::createEmitter)
                    .send(event().name(event.getEventName()).data(event));
        }
    }

    private SseEmitter createEmitter(String playerKey) {
        SseEmitter emitter = new SseEmitter(0L);

        emitter.onCompletion(() -> playerEventStreams.remove(playerKey, emitter));
        emitter.onTimeout(() -> playerEventStreams.remove(playerKey, emitter));
        emitter.onError((ex) -> playerEventStreams.remove(playerKey, emitter));

        return emitter;
    }
}
