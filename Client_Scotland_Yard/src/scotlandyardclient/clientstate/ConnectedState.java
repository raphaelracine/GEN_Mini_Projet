package scotlandyardclient.clientstate;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardclient.Client;

public abstract class ConnectedState extends ClientState {

    private Socket socket;

    public ConnectedState(Socket socket) {
        this.socket = socket;
    }

    public void connect(String ipAddress, int port) {
    }

    public void disconnect() {
        try {
            socket.close();
            Client.getInstance().setState(new DisconnectedState());
        } catch (IOException e) {
            Logger.getLogger(ConnectedState.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void sendCommand(String command) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(command);
        } catch (IOException e) {
            Logger.getLogger(ConnectedState.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String receiveCommand() {
        String str = null;

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            str = is.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ConnectedState.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public boolean isConnected() {
        return !socket.isClosed() && socket.isConnected();
    }

    public Socket getSocket() {
        return socket;
    }
}
