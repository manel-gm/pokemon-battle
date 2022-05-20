package com.example.pokemon;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectionListTest {

    @Test
    void shouldGenerate50Pokemon() {
        RestTemplate restTemplate = new RestTemplate();
        SelectionList availablePokemon = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/?limit=1", SelectionList.class);

        assertNotEquals(0, availablePokemon.getCount(), "The count has not been properly initialized");

        List<Pokemon> chosenOnes = availablePokemon.choose50random();

        assertEquals(50, chosenOnes.size(), "There should be 50 pokemon in the list");
    }
}