/**
 * Cette classe représentant l'état d'une partie en cours de jeu (State Pattern)
 *
 * Il est à noter que toutes ces méthodes sont décrites dans la classe Game mais
 * elle sont appelées par délégation
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
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

    /**
     * Constructeur
     *
     * @param game La partie concernée
     */
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

    /**
     * Permet de demander au prochain joueur de jouer
     */
    private void requestNextPlayerToPlay() {
        Client next = turns.remove();

        // Si c'est Mister X, on passe au prochain tour
        if (game().getMisterX().getPlayer() == next) {
            turnNumber++;
        }

        for (Client c : game().players()) {
            if (c == next) {
                c.sendMessage("YOUR_TURN#");
            } else {
                c.sendMessage("NOT_YOUR_TURN#" + next.username());
            }
        }

        turns.add(next);
    }
}
