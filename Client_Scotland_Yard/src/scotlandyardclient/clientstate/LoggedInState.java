package scotlandyardclient.clientstate;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.Socket;
import scotlandyardclient.Client;
import scotlandyardclient.json.MapNames;

public class LoggedInState extends ConnectedState {

    private String username;

    public LoggedInState(Socket socket, String username) {
        super(socket);
        this.username = username;
    }

    @Override
    public boolean logIn(String username, String password) {
        // On ne fait rien car le client est déjà authentifié
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

        if (response.equals("EDITACCOUNTACCEPTED")) {
            this.username = newUsername;
            return true;
        } else if (response.equals("EDITACCOUNTREFUSED")) {
            return false;
        }
        return false;
    }
    @Override
    public boolean createGame(String partyName, int playersNb, String map){
        sendCommand("CREATEGAME#" + partyName + "#" + playersNb + "#"+ map);
        String response = receiveCommand();

        if (response.equals("CREATEGAMEACCEPTED")) {
     
            return true;
        } else if (response.equals("CREATEGAMEREFUSED")) {
            return false;
        }
        return false;
    }
    
    @Override
    public MapNames getMapNames() {
        return new Gson().fromJson(receiveCommand(), MapNames.class);
    }
}
