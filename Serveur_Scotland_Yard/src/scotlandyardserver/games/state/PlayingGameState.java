/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardserver.games.state;

import java.util.HashMap;
import java.util.LinkedList;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

import java.util.Queue;
import scotlandyardserver.games.DetectivePone;

/**
 *
 * @author Raphaël Racine
 */
public class PlayingGameState extends GameState {

    private final Queue<Client> turns = new LinkedList<>();
    private int turnNumber;

    private static final HashMap<Integer, Boolean> turnsToShowMisterX = new HashMap<>();

    public PlayingGameState(Game game) {
        super(game);

        // Mister X joue en premier
        turns.add(game.getMisterX().getPlayer());

        // Ensuite les détectives dans l'ordre
        for (DetectivePone p : game.getDetectives()) {
            turns.add(p.getPlayer());
        }

        turnsToShowMisterX.put(3, true);
        turnsToShowMisterX.put(8, true);
        turnsToShowMisterX.put(13, true);
        turnsToShowMisterX.put(18, true);

        requestNextPlayerToPlay();
    }

    @Override
    public boolean joinGame(Client client) {
        return false;
    }

    @Override
    public void leaveGame(Client client) {
        /* TODO */
    }

    private void requestNextPlayerToPlay() {
        Client next = turns.remove();

        // Si c'est Mister X, on passe au prochain tour
        if (game().getMisterX().getPlayer() == next) {
            turnNumber++;
        }

        for (Client c : game().players()) {
            if (c == next)
                c.sendMessage("YOUR_TURN#");
            else
                c.sendMessage("NOT_YOUR_TURN#" + next.username());
        }
        
        turns.add(next);
    }
}
