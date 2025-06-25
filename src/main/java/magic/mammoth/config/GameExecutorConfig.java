package magic.mammoth.config;

import org.apache.tomcat.util.threads.VirtualThreadExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class GameExecutorConfig {

    @Bean
    public Executor gameExecutor() {
        return new VirtualThreadExecutor("game-");
    }
}
