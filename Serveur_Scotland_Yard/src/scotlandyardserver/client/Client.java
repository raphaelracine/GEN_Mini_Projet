package scotlandyardserver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.Server;
import scotlandyardserver.client.state.ClientLoggedOut;
import scotlandyardserver.client.state.ClientState;

public class Client implements Runnable {

    private Socket socket;
    private Thread thread;

    private ClientState state;

    private BufferedReader in;
    private PrintWriter out;
    private Server server;

    public Client(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;

        state = new ClientLoggedOut(this);

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            thread = new Thread(this);
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String command;
        boolean shouldRun = true;

        try {
            while ((shouldRun) && (command = in.readLine()) != null) {
                if (command.equalsIgnoreCase("bye")) {
                    shouldRun = false;
                    logOut();
                } else {
                    server.interpreteReceivedCommand(command, this);
                }
            }
            
            logOut();
            socket.close();
            in.close();
            out.close();

        } catch (IOException ex) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                }
            }
            if (out != null) {
                out.close();
            }

            if (socket != null) {
                try {

                    socket.close();
                } catch (IOException ex1) {
                }
            }
        }
    }

    public void sendMessage(String message) {
        System.out.println("Username : " + username() + " Message : " + message);
        out.println(message);
        //out.flush();
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public void logIn(String username, String password) {
        state.logIn(username, password);
    }

    public void logOut() {
        state.logOut();
    }

    public String username() {
        return state.username();
    }

    public Server server() {
        return server;
    }

    public void setUsername(String newUsername) {
        state.setUsername(newUsername);
    }

    public void leaveGame() {
        state.leaveGame();
    }
    
    public void createGame(String name, int numberOfPlayers, String map) {
        state.createGame(name, numberOfPlayers, map);
    }
    
    public void joinGame(String name) {
        state.joinGame(name);
    }
}
