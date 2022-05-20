package com.example.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectionList {

    private static final Random RNG = new Random();
    private static final int POKEMON_NEEDED = 50;

    @Getter
    private int count;

    public SelectionList() {

    }

    public List<Pokemon> choose50random() {
        RestTemplate restTemplate = new RestTemplate();
        int[] ids = get50RandomIds();
        List<Pokemon> pokemonList = new ArrayList<>();
        for (int id : ids) {
            pokemonList.add(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/" + id, Pokemon.class));
        }
        return pokemonList;
    }

    private int[] get50RandomIds() {
        int[] ids = new int[POKEMON_NEEDED];
        int i = 0;
        int requiredRemaining = POKEMON_NEEDED;
        int available = count;
        for (int j = 0; j < count && requiredRemaining > 0; j++) {
            double probability = RNG.nextDouble();
            if (probability < ((double) POKEMON_NEEDED) / (double) available) {
                requiredRemaining--;
                ids[i++] = j;
            }
            available--;
        }
        return ids;
    }
}
