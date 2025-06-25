package magic.mammoth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameTests {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void createGame() throws Exception {
        mockMvc.perform(post("/api/create-game"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Game-Key"));
    }

    @Test
    void joinGame() throws Exception {
        String gameKey = mockMvc.perform(post("/api/create-game"))
                .andReturn().getResponse().getHeader("Game-Key");

        mockMvc.perform(post("/api/join-game/{gameKey}?player=Doraemon", gameKey))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Game-Key"))
                .andExpect(header().exists("Player-Key"));
    }

    @Test
    void gameDetails() throws Exception {
        String gameKey = mockMvc.perform(post("/api/create-game"))
                .andReturn().getResponse().getHeader("Game-Key");
        String playerKey = mockMvc.perform(post("/api/join-game/{gameKey}?player=Doraemon", gameKey))
                .andReturn().getResponse().getHeader("Player-Key");
        mockMvc.perform(post("/api/join-game/{gameKey}?player=Nobita", gameKey));

        mockMvc.perform(post("/api/game-details")
                        .header("Game-Key", gameKey)
                        .header("Player-Key", playerKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"players\":[{\"name\":\"Doraemon\"},{\"name\":\"Nobita\"}]}"));
    }
}
