/**
 * Cette classe abstraite représente l'état du client (State Pattern)
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

public abstract class ClientState {

    private final Client client;

    /**
     * Constructeur
     *
     * @param client Le client concerné
     */
    public ClientState(Client client) {
        this.client = client;
    }

    public abstract void logIn(String username, String password);

    public abstract void logOut();

    public abstract String username();

    public Client client() {
        return client;
    }

    public abstract void setUsername(String newUsername);

    public abstract void leaveGame();

    public abstract void createGame(String name, int numberOfPlayers, String map);

    public abstract void joinGame(String name);
}
