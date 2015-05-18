package scotlandyardclient.clientstate;

import java.net.Socket;
import scotlandyardclient.Client;

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
      @Override
    public boolean createGame(String partyName, int playersNb, String map){
        return false;
    }
}
