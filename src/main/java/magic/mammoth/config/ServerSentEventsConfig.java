package magic.mammoth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ServerSentEventsConfig {

    // TODO is this a good idea? (removes communications / mvc logic from model)
    @Bean
    public Map<UUID, SseEmitter> playerEventStreams() {
        return new ConcurrentHashMap<>();
    }
}
