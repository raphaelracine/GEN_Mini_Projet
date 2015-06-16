/**
 * Classe qui représente l'état d'un client lorsqu'il est authentifié sur un
 * serveur (utilisation du State Pattern).
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

import com.google.gson.Gson;
import java.net.Socket;
import scotlandyardclient.Client;
import scotlandyardclient.json.*;
import scotlandyardclient.pone.Pone;

public class LoggedInState extends ConnectedState {

    private String username; // Le nom d'utilisateur du client sur le serveur

    /**
     * Constructeur
     *
     * @param socket Socket de connexion vers le serveur
     * @param username Nom d'utilisateur
     */
    public LoggedInState(Socket socket, String username) {
        super(socket);
        this.username = username;
    }

    @Override
    public boolean logIn(String username, String password) {
        return true;
    }

    @Override
    public void logOut() {
        sendCommand("UNAUTHENTICATE");
        Client.getInstance().setState(new LoggedOutState(getSocket()));
    }

    @Override
    public boolean createAccount(String username, String password) {
        return false;
    }

    @Override
    public boolean editAccount(String newUsername, String newPassword) {
        sendCommand("EDITACCOUNT#" + newUsername + "#" + newPassword);
        String response = receiveCommand();

        switch (response) {
            case "EDITACCOUNTACCEPTED":
                this.username = newUsername;
                return true;
            case "EDITACCOUNTREFUSED":
                return false;
        }
        return false;
    }

    @Override
    public boolean joinGame(String gameName) {
        sendCommand("JOINGAME#" + gameName);
        String response = receiveCommand();

        switch (response) {
            case "JOINGAMEACCEPTED":
                Client.getInstance().setState(new LoggedInGameState(getSocket(), username()));
                return true;
            case "JOINGAMEREFUSED":
                return false;
        }
        return false;
    }

    @Override
    public boolean createGame(String partyName, int playersNb, String map) {
        sendCommand("CREATEGAME#" + partyName + "#" + playersNb + "#" + map);
        String response = receiveCommand();

        switch (response) {
            case "CREATEGAMEACCEPTED":
                Client.getInstance().setState(new LoggedInGameState(getSocket(), username()));
                return true;
            case "CREATEGAMEREFUSED":
                return false;
        }

        return false;
    }

    @Override
    public MapNames getMapNames() {
        return new Gson().fromJson(receiveCommand(), MapNames.class);
    }

    @Override
    public void leaveGame() {
    }

    @Override
    public GameList getGameList() {
        sendCommand("REQUESTGAMELIST");
        return new Gson().fromJson(receiveCommand(), GameList.class);
    }

    @Override
    public PlayerList getPlayerList(String game) {
        sendCommand("REQUESTPLAYERLIST#" + game);
        return new Gson().fromJson(receiveCommand(), PlayerList.class);
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public Pone getPone() {
        return null;
    }

    @Override
    public void setPone(Pone p) {
    }
}
