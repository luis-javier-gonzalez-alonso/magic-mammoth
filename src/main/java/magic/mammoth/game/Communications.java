package magic.mammoth.game;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import magic.mammoth.game.events.GameEvent;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Comparator.comparingLong;
import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

@RequiredArgsConstructor
public class Communications {

    private final String gameKey;

    // Game communications ----------------------------------------------------

    // Fair keeps order of lock
    private final ReentrantLock inputCommunication = new ReentrantLock(true);
    private final BlockingQueue<GameEvent> communications = new LinkedBlockingQueue<>();

    public void submitEvent(GameEvent event) {
        inputCommunication.lock();

        try {
            // Override to make consistent with order of lock (fair)
            event.setTimestamp(System.nanoTime());
            communications.add(event);

        } finally {
            inputCommunication.unlock();
        }
    }

    public GameEvent waitForEvent() throws InterruptedException {
        return communications.take();
    }

    public GameEvent waitForEvent(Duration timeout) throws InterruptedException {
        return communications.poll(timeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    // Player communications --------------------------------------------------

    private final ConcurrentMap<String, SseEmitter> playerEventStreams = new ConcurrentHashMap<>();
    private final ConcurrentNavigableMap<EventKey, Set<ResponseBodyEmitter.DataWithMediaType>> gameEvents =
            new ConcurrentSkipListMap<>(comparingLong(EventKey::timestamp));

    public SseEmitter getEventStream(String playerKey, String lastEventId) {
        SseEmitter eventStream = playerEventStreams.computeIfAbsent(playerKey, this::createEmitter);

        if (lastEventId == null || lastEventId.isEmpty()) {
            // Initial connection
            gameEvents.forEach((eventId, event) -> send(playerKey, event));
        } else {
            // Reconnect
            gameEvents.tailMap(EventKey.parse(lastEventId), false)
                    .forEach((eventId, event) -> send(playerKey, event));
        }

        return eventStream;
    }

    public void send(String playerKey, GameEvent event) {
        EventKey eventKey = new EventKey(gameKey, event.getTimestamp());
        var finalEvent = gameEvents.putIfAbsent(eventKey, event()
                .id(eventKey.toString())
                .name(event.getEventName())
                .data(event)
                .build());

        send(playerKey, finalEvent);
    }

    @SneakyThrows
    private void send(String playerKey, Set<ResponseBodyEmitter.DataWithMediaType> event) {
        try {
            SseEmitter sseEmitter = playerEventStreams.get(playerKey);
            if (sseEmitter != null) {
                sseEmitter.send(event);
            }

        } catch (IllegalStateException exception) {
            // already stored, reconnect will trigger recovery
        }
    }

    private SseEmitter createEmitter(String playerKey) {
        SseEmitter emitter = new SseEmitter(0L);

        emitter.onCompletion(() -> playerEventStreams.remove(playerKey, emitter));
        emitter.onTimeout(() -> playerEventStreams.remove(playerKey, emitter));
        emitter.onError((ex) -> playerEventStreams.remove(playerKey, emitter));

        return emitter;
    }

    private record EventKey(@NonNull String gameKey, long timestamp) {

        public static EventKey parse(String value) {
            String[] split = value.split("-");
            return new EventKey(split[0], Long.parseLong(split[1]));
        }

        @NonNull
        @Override
        public String toString() {
            return gameKey + "-" + timestamp;
        }
    }
}
