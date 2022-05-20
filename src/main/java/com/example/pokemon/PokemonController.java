package com.example.pokemon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Controller
public class PokemonController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/select")
    public String get50Pokemon(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        SelectionList availablePokemon = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/?limit=1", SelectionList.class);

        if (availablePokemon != null) {
            List<Pokemon> chosenOnes = availablePokemon.choose50random();
            model.addAttribute("pokemonList", chosenOnes);
        } else {
            log.error("Could not get a valid response from https://pokeapi.co/api/v2/pokemon/?limit=1");
            return "error";
        }

        model.addAttribute("battle", new Battle());

        return "select";
    }

    @PostMapping("/battle")
    public String battle(@ModelAttribute Battle battle, Model model) {
        battle.setPokemon();
        battle.fight();
        model.addAttribute("battle", battle);
        return "battle";
    }

}
