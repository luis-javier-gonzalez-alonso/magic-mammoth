package magic.mammoth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameIntegrationTests {

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
    void game() throws Exception {
        String gameKey = mockMvc.perform(post("/api/create-game"))
                .andReturn().getResponse().getHeader("Game-Key");

        String playerKey = mockMvc.perform(post("/api/join-game/{gameKey}?player=Doraemon", gameKey))
                .andReturn().getResponse().getHeader("Player-Key");

        mockMvc.perform(get("/api/start-game")
                        .header("Game-Key", gameKey)
                        .header("Player-Key", playerKey))
                .andExpect(status().isNoContent());

        MvcResult result = mockMvc.perform(get("/api/game")
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .header("Game-Key", gameKey)
                        .header("Player-Key", playerKey))
                .andExpect(status().isOk())
                .andReturn();

        String generateEvents = result.getResponse().getContentAsString();

        assertEquals("""
                event:player-joined
                data:{"playerName":"Doraemon"}
                                
                event:game-started
                data:{}
                                
                """, generateEvents);
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
