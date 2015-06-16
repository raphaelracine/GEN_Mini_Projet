/**
 * Cette classe représente un client qui se connecte au serveur
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    /**
     * Constructeur
     *
     * @param socket Socket pour la connexion avec le client
     * @param server Référence vers le serveur
     */
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

    /**
     * Réception des messages du client
     */
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

    /**
     * Permet d'envoyer un message au client
     *
     * @param message Le message à envoyer au client
     */
    public void sendMessage(String message) {
        out.println(message);

        /**
         * BUG SI ON ENLEVE CETTE LIGNE !!! *
         */
        System.out.println("Envoi de " + message);
    }

    /**
     * Permet d'envoyer une image au client
     *
     * @param fileName Nom du fichier image
     * @throws IOException
     */
    public void sendImageFile(String fileName) throws IOException {
        FileInputStream file = null;
        try {
            byte buffer[] = new byte[1024];
            file = new FileInputStream(fileName);
            OutputStream os = socket.getOutputStream();
            int n;
            sendMessage("IMAGESIZE#" + new File(fileName).length());
            while ((n = file.read(buffer)) != -1) {
                System.out.println(n);
                os.write(buffer, 0, n);
            }
            os.flush();
            file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Permet de changer l'état du client (State Pattern)
     *
     * @param state Le nouvel état du client
     */
    public void setState(ClientState state) {
        this.state = state;
    }

    /**
     * Permet à un client de s'authentifier sur le serveur
     *
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     */
    public void logIn(String username, String password) {
        state.logIn(username, password);
    }

    /**
     * Permet au client de se déconnecter de son compte
     */
    public void logOut() {
        state.logOut();
    }

    /**
     * Permet d'obtenir le nom d'utilisateur du client s'il est connecté
     *
     * @return Le nom d'utilisateur sur lequel il est connecté, null si non
     * connecté
     */
    public String username() {
        return state.username();
    }

    /**
     * Retourne une référence vers le serveur du client
     *
     * @return Référence vers le serveur
     */
    public Server server() {
        return server;
    }

    /**
     * Permet de changer le nom d'utilisateur du client
     *
     * @param newUsername Le nouveau nom d'utilisateur
     */
    public void setUsername(String newUsername) {
        state.setUsername(newUsername);
    }

    /**
     * Permet au client de quitter la partie dans laquelle il se trouve
     * actuellement
     */
    public void leaveGame() {
        state.leaveGame();
    }

    /**
     * Permet au client de créer une partie
     *
     * @param name Nom de la partie
     * @param numberOfPlayers Le nombre de joueurs
     * @param map Le nom de la carte
     */
    public void createGame(String name, int numberOfPlayers, String map) {
        state.createGame(name, numberOfPlayers, map);
    }

    /**
     * Permet au client de rejoindre une partie
     *
     * @param name Le nom de la partie
     */
    public void joinGame(String name) {
        state.joinGame(name);
    }
}
