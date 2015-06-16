/**
 * Classe qui représente l'état d'un client lorsqu'il est dans une partie
 * (utilisation du State Pattern).
 *
 * Il est à noter que toutes les méthodes de cette classe sont également dans la
 * classe Client, mais ces méthodes sont appelées par délégation
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.clientstate;

import java.net.Socket;
import scotlandyardclient.Client;
import scotlandyardclient.pone.Pone;

public class LoggedInGameState extends LoggedInState {

    private Pone pone; // Le pion qu'il joue dans la partie

    /**
     * Constructeur
     *
     * @param socket La socket de connexion vers le serveur
     * @param username Le nom d'utilisateur du client
     */
    public LoggedInGameState(Socket socket, String username) {
        super(socket, username);
    }

    @Override
    public boolean joinGame(String name) {
        return false;
    }

    @Override
    public void leaveGame() {
        Client.getInstance().sendCommand("PLAYERLEAVEGAME");
        Client.getInstance().setState(new LoggedInState(getSocket(), username()));
    }

    @Override
    public boolean createGame(String name, int nbPlayers, String map) {
        return false;
    }

    @Override
    public void setPone(Pone p) {
        this.pone = p;
    }

    @Override
    public Pone getPone() {
        return pone;
    }
}
