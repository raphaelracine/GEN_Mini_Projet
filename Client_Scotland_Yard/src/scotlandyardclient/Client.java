package scotlandyardclient;

import scotlandyardclient.clientstate.ClientState;
import scotlandyardclient.clientstate.DisconnectedState;

public class Client {

    private static Client client;
    private ClientState state;

    private Client() {
        state = new DisconnectedState();
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public void connect(String ipAddress, int port) {
        state.connect(ipAddress, port);
    }

    public void disconnect() {
        state.disconnect();
    }

    public boolean isConnected() {
        return state.isConnected();
    }

    public boolean createAccount(String username, String password) {
        return state.createAccount(username, password);
    }

    public boolean editAccount(String newUsername, String newPassword) {
        return state.editAccount(newUsername, newPassword);
    }
    public boolean createGame(String partyName, int playersNb, String map){
       return state.createGame(partyName, playersNb, map);
    }
    public boolean logIn(String username, String password) {
        return state.logIn(username, password);
    }

    public void logOut() {
        state.logOut();
    }

    public void sendCommand(String command) {
        state.sendCommand(command);
    }

}
