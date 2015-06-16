/**
 * Classe abstraite qui représente l'état d'un client (utilisation du State
 * Pattern).
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

import scotlandyardclient.json.*;
import scotlandyardclient.pone.Pone;

public abstract class ClientState {

    /* La description de ces méthodes se trouve dans la classe Client */
    public abstract void connect(String ipAddress, int port);

    public abstract void disconnect();

    public abstract String receiveCommand();

    public abstract void sendCommand(String command);

    public abstract boolean isConnected();

    public abstract boolean logIn(String username, String password);

    public abstract void logOut();

    public abstract boolean createAccount(String username, String password);

    public abstract boolean editAccount(String newUsername, String newPassword);

    public abstract boolean joinGame(String gameName);

    public abstract boolean createGame(String partyName, int playersNb, String map);

    public abstract MapNames getMapNames();

    public abstract GameList getGameList();

    public abstract PlayerList getPlayerList(String game);

    public abstract String username();

    public abstract void leaveGame();

    public abstract byte[] receiveImage();

    public abstract Pone getPone();

    public abstract void setPone(Pone p);
}
