package scotlandyardclient.clientstate;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardclient.Client;

public class DisconnectedState extends ClientState {

    public void connect(String ipAddress, int port) {
        Client.getInstance().setState(this);
        Socket socket;
        InetAddress ip;
        try {
            ip = InetAddress.getByName(ipAddress);
            socket = new Socket(ip, port);
            Client.getInstance().setState(new LoggedOutState(socket));
        } catch (UnknownHostException e) {
            Logger.getLogger(DisconnectedState.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            Logger.getLogger(DisconnectedState.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void disconnect() {
    }

    public void sendCommand(String command) {
    }

    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean logIn(String username, String password) {
        // On ne fait rien car le client n'est pas connecté
        return false;
    }

    @Override
    public void logOut() {
        // On ne fait rien car le client n'est pas connecté
    }

    @Override
    public boolean createAccount(String username, String password) {
        return false;
    }

    @Override
    public boolean editAccount(String newUsername, String newPassword) {
        return false;
    }
      @Override
    public boolean createGame(String command) {
        return false;
    }
}
