/**
 * Cette classe représente l'état du client lorsqu'il est authentifié et se
 * trouve dans une partie (State Pattern)
 *
 * Toutes les méthodes de cette classes sont expliquées dans la classe Client
 * car elle sont simplement appelées par délégation
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.client.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public class ClientLoggedInAGame extends ClientLoggedIn {

    private final Game game; // Partie dans laquelle il se trouve

    /**
     * Constructeur
     *
     * @param client Le client concerné
     * @param username LE nom d'utilisateur
     * @param game La partie concernée
     */
    public ClientLoggedInAGame(Client client, String username, Game game) {
        super(client, username);
        this.game = game;
    }

    @Override
    public void createGame(String name, int numberOfPlayers, String map) {
    }

    @Override
    public void joinGame(String name) {
    }

    @Override
    public void leaveGame() {
        game.leaveGame(client());
    }
}
