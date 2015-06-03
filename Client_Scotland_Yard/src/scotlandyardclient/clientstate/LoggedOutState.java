package scotlandyardclient.clientstate;

import java.io.IOException;
import java.net.Socket;
import scotlandyardclient.Client;
import scotlandyardclient.json.*;
import scotlandyardclient.pone.Pone;

class LoggedOutState extends ConnectedState {

    public LoggedOutState(Socket socket) {
        super(socket);
    }

    @Override
    public boolean logIn(String username, String password) {
        sendCommand("AUTHENTICATE#" + username + "#" + password);
        String response = receiveCommand();

        if (response.equals("AUTHENTICATEACCEPTED")) {
            Client.getInstance().setState(new LoggedInState(getSocket(), username));
            return true;
        } else if (response.equals("AUTHENTICATEREFUSED")) {
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

        if (response.equals("CREATEACCOUNTACCEPTED")) {
            return true;
        } else if (response.equals("CREATEACCOUNTREFUSED")) {
            return false;
        }
        return false;
    }

    @Override
    public boolean editAccount(String newUsername, String newPassword) {
        return false;
    }
    
    public boolean joinGame(String gameName) {
        return false;
    }
    
    @Override
    public boolean createGame(String partyName, int playersNb, String map){
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
    public void leaveGame() {}
    
    @Override
    public Pone getPone() {
        return null;
    }
    
    @Override
     public void setPone(Pone p) {}
}
