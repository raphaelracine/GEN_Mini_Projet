/**
 * Classe qui représente l'état d'un client lorsqu'il est connecté à un serveur
 * mais pas authentifié (utilisation du State Pattern).
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
import scotlandyardclient.json.*;
import scotlandyardclient.pone.Pone;

class LoggedOutState extends ConnectedState {

    /**
     * Constructeur
     *
     * @param socket Socket de connexion vers le serveur
     */
    public LoggedOutState(Socket socket) {
        super(socket);
    }

    @Override
    public boolean logIn(String username, String password) {
        sendCommand("AUTHENTICATE#" + username + "#" + password);
        String response = receiveCommand();

        switch (response) {
            case "AUTHENTICATEACCEPTED":
                Client.getInstance().setState(new LoggedInState(getSocket(), username));
                return true;
            case "AUTHENTICATEREFUSED":
                return false;
        }

        return false;
    }

    @Override
    public void logOut() {

    }

    @Override
    public boolean createAccount(String username, String password) {
        sendCommand("CREATEACCOUNT#" + username + "#" + password);
        String response = receiveCommand();

        switch (response) {
            case "CREATEACCOUNTACCEPTED":
                return true;
            case "CREATEACCOUNTREFUSED":
                return false;
        }
        return false;
    }

    @Override
    public boolean editAccount(String newUsername, String newPassword) {
        return false;
    }

    @Override
    public boolean joinGame(String gameName) {
        return false;
    }

    @Override
    public boolean createGame(String partyName, int playersNb, String map) {
        return false;
    }

    @Override
    public MapNames getMapNames() {
        return null;
    }

    @Override
    public GameList getGameList() {
        return null;
    }

    @Override
    public PlayerList getPlayerList(String game) {
        return null;
    }

    @Override
    public String username() {
        return null;
    }

    @Override
    public void leaveGame() {
    }

    @Override
    public Pone getPone() {
        return null;
    }

    @Override
    public void setPone(Pone p) {
    }
}
