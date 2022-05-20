package com.example.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Pokemon {

    private static final Random RNG = new Random();

    private long id;
    private String name;
    private int height;
    private int weight;
    private JsonNode sprites;

    @Setter
    private int hp;
    @Setter
    private State state;

    public Pokemon() {

    }

    public String selectRandomAttack() {
        return RNG.nextInt(100) < 25 ? "special" : "normal";
    }

    public int attack(Pokemon opponent, String attackType) {
        return attackType.equals("normal") ? normalAttack(opponent) : specialAttack(opponent);
    }

    private int normalAttack(Pokemon opponent) {
        int damage = RNG.nextInt(10) + 1;
        opponent.setHp(opponent.getHp() - damage);
        return damage;
    }

    private int specialAttack(Pokemon opponent) {
        int damage = RNG.nextInt(11) + 5;
        opponent.setHp(opponent.getHp() - damage);
        return damage;
    }

    public boolean hasFainted() {
        return hp <= 0;
    }

    public void resetHp() {
        hp = 20;
    }

    public String getListDescription() {
        return name + "\n" +
                "height: " + height + " dm" +
                " / weight: " + weight + " hg";
    }

    public String getArtwork() {
        String url =  sprites.get("other").get("official-artwork").get("front_default").toString();
        return url.substring(1, url.length()-1);
    }

    public enum State {
        READY,
        CHARGING
    }

}
