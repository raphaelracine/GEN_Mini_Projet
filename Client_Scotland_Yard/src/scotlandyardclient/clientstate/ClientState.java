package scotlandyardclient.clientstate;

import java.io.IOException;
import scotlandyardclient.json.MapNames;

public abstract class ClientState {

    public abstract void connect(String ipAddress, int port);

    public abstract void disconnect();

    public abstract void sendCommand(String command);

    public abstract boolean isConnected();

    public abstract boolean logIn(String username, String password);

    public abstract void logOut();

    public abstract boolean createAccount(String username, String password);

    public abstract boolean editAccount(String newUsername, String newPassword);
    
    public abstract boolean createGame(String partyName, int playersNb, String map);

    public abstract MapNames getMapNames();

}
