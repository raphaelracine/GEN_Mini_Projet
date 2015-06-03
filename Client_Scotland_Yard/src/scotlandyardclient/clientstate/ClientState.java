package scotlandyardclient.clientstate;

import scotlandyardclient.json.*;
import scotlandyardclient.pone.Pone;

public abstract class ClientState {

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
