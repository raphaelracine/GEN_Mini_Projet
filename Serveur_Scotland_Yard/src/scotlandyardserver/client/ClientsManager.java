package scotlandyardserver.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.Server;

/**
 *
 * @author Raphaël Racine
 */
public class ClientsManager implements Runnable {

    private final Thread thread;
    private final LinkedList<Client> clients = new LinkedList<>();

    private final Server server;

    public ClientsManager(Server server) {
        this.server = server;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            /* Réception des clients */
            ServerSocket serverSocket = new ServerSocket(server.getPort());

            while (true) {
                /* Attente du prochain client */
                Socket clientSocket = serverSocket.accept();
                clients.add(new Client(clientSocket, server));
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LinkedList<Client> listClients() {
        return clients;
    }
}
