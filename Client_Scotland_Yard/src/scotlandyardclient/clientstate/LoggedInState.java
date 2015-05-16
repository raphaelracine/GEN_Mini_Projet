package scotlandyardclient.clientstate;

import java.net.Socket;
import scotlandyardclient.Client;

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
    public boolean createGame(String command){
        sendCommand(command);
        String response = receiveCommand();

        if (response.equals("CREATEGAMEACCEPTED")) {
     
            return true;
        } else if (response.equals("CREATEGAMEREFUSED")) {
            return false;
        }
        return false;
    }

}
