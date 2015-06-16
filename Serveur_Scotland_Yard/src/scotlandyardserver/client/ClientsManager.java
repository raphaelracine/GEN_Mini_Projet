/**
 * Cette classe représente un manager de clients, qui s'occupe de recevoir les
 * nouveaux clients qui se connectent au serveur
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.Server;

public class ClientsManager implements Runnable {

    private final Thread thread;
    private final LinkedList<Client> clients = new LinkedList<>();

    private final Server server;

    /**
     * Constructeur
     *
     * @param server Référence vers le serveur
     */
    public ClientsManager(Server server) {
        this.server = server;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Attente active de clients qui se connecte sur la socket du serveur
     */
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

    /**
     * Retourne la liste des clients connectés au serveur
     *
     * @return Liste des clients connectés au serveur
     */
    public LinkedList<Client> listClients() {
        return clients;
    }
}
