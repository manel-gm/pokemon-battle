package com.example.pokemon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnIndexPage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("POKEMON NOT-STADIUM")));
    }

    @Test
    public void shouldReturnErrorPage() throws Exception {
        this.mockMvc.perform(get("/error")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Poke Battle - Error")));
    }

    @Test
    public void shouldReturnSelectPage() throws Exception {
        this.mockMvc.perform(get("/select")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Poke Battle - Choose your Pokemon")));
    }

    @Test
    public void shouldReturnBattlePage() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id1", "1");
        requestParams.add("id2", "12");

        this.mockMvc.perform(post("/battle").params(requestParams)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Poke Battle - Battle")));
    }

}
