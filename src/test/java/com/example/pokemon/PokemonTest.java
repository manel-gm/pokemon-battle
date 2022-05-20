package com.example.pokemon;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void attackShouldDamageOpponent() {
        RestTemplate restTemplate = new RestTemplate();
        Pokemon pokemon = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/23", Pokemon.class);
        Pokemon opponent = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/43", Pokemon.class);

        opponent.resetHp();
        int damage = pokemon.attack(opponent, "normal");
        assertEquals(20 - damage, opponent.getHp(), "Opponent did not lose proper hp");

        opponent.resetHp();
        damage = pokemon.attack(opponent, "special");
        assertEquals(20 - damage, opponent.getHp(), "Opponent did not lose proper hp");
    }
}