/**
 * Cette classe représentant l'état d'une partie (State Pattern)
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

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public abstract class GameState {
    
    private final Game game;
    
    /**
     * Constructeur
     * @param game La partie dont on veut gérer l'état
     */
    public GameState(Game game) {
        this.game = game;
    }
    
    /**
     * Permet d'obtenir la partie
     * @return La partie concernée
     */
    public Game game() {
        return game;
    }

    public abstract boolean joinGame(Client client);

    public abstract void leaveGame(Client client);
}
