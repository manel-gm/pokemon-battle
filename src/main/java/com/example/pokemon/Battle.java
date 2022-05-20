package com.example.pokemon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Battle {

    @Setter
    private long id1;
    @Setter
    private long id2;

    private Pokemon player1;
    private Pokemon player2;

    @Getter(AccessLevel.NONE)
    private Map<Pokemon, Integer> roundWins = new HashMap<>();

    private String fightLog = "";

    public Battle() {

    }

    public void setPokemon() {
        RestTemplate restTemplate = new RestTemplate();
        player1 = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/" + id1, Pokemon.class);
        player2 = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/" + id2, Pokemon.class);
    }

    public void fight() {
        roundWins.put(player1, 0);
        roundWins.put(player2, 0);
        int i = 1;
        do {
            announceRound(i);
            Pokemon winner = fightRound();
            announceRoundWinner(i++, winner);
        } while (roundWins.get(player1) < 2 && roundWins.get(player2) < 2);
        announceBattleWinner();
    }

    private void announceRound(int i) {
        fightLog += "ROUND " + i + "\n\n";
    }

    private Pokemon fightRound() {
        initializePokemon();
        boolean p1Turn = true;
        String attackType;
        Pokemon winner;
        do {
            if (p1Turn) {
                p1Turn = false;
                attackType = determineAttackType(player1);
                winner = attack(player1, player2, attackType);
            } else {
                p1Turn = true;
                attackType = determineAttackType(player2);
                winner = attack(player2, player1, attackType);
            }
        } while (winner == null);
        return winner;
    }

    private void initializePokemon() {
        player1.resetHp();
        player1.setState(Pokemon.State.READY);
        player2.resetHp();
        player2.setState(Pokemon.State.READY);
    }

    private String determineAttackType(Pokemon pokemon) {
        String attackType;
        if (!pokemon.getState().equals(Pokemon.State.CHARGING)) {
            attackType = pokemon.selectRandomAttack();
            if (attackType.equals("special")) {
                pokemon.setState(Pokemon.State.CHARGING);
                attackType = "none";
                fightLog += pokemon.getName() + " is charging for a special attack!\n\n";

            }
        } else {
            pokemon.setState(Pokemon.State.READY);
            attackType = "special";
            fightLog += pokemon.getName() + " unleashes its special attack!\n";
        }
        return attackType;
    }

    private Pokemon attack(Pokemon attacker, Pokemon opponent, String attackType) {
        Pokemon winner = null;
        if (!attackType.equals("none")) {
            int damage = attacker.attack(opponent, attackType);
            fightLog += attacker.getName() + " hit " + opponent.getName() + " for " + damage + " damage.\n";
            if (opponent.hasFainted()) {
                fightLog += "\n" + opponent.getName() + " has fainted.\n\n\n";
                roundWins.merge(attacker, 1, Integer::sum);
                winner = attacker;
            } else {
                fightLog += opponent.getName() + " has " + opponent.getHp() + " hp remaining.\n\n";
            }
        }
        return winner;
    }

    private void announceRoundWinner(int i, Pokemon winner) {
        fightLog += winner.getName() + " wins round " + i + "!\n\n\n";
    }

    private void announceBattleWinner() {
        fightLog += (roundWins.get(player1) == 2 ? player1.getName() + " (P1)" : player2.getName() + " (P2)") + " is the grand winner!";
    }

}
