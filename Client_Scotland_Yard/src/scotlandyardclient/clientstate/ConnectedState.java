/**
 * Classe qui représente l'état d'un client lorsqu'il est connecté à un serveur
 * (utilisation du State Pattern).
 *
 * Il est à noter que toutes les méthodes de cette classe sont également dans la
 * classe Client, mais ces méthodes sont appelées par délégation
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.clientstate;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardclient.Client;

public abstract class ConnectedState extends ClientState {

    private final Socket socket; // La socket qu'il utilise pour comuniquer avec le serveur

    /**
     * Constructeur
     *
     * @param socket La socket pour la connexion avec le serveur
     */
    public ConnectedState(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void connect(String ipAddress, int port) {
    }

    @Override
    public void disconnect() {
        try {
            socket.close();
            Client.getInstance().setState(new DisconnectedState());
        } catch (IOException e) {
            Logger.getLogger(ConnectedState.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void sendCommand(String command) {
        System.out.println("ENVOI DE LA COMMANDE :" + command);

        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(command);
            pw.flush();
        } catch (IOException e) {
            Logger.getLogger(ConnectedState.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public String receiveCommand() {
        String str = null;

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Attend un message");
            str = is.readLine();
            System.out.println("Recu " + str);
        } catch (IOException ex) {
            Logger.getLogger(ConnectedState.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    @Override
    public byte[] receiveImage() {

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());

            String fileSize = receiveCommand();
            int size = Integer.parseInt(fileSize.split("#")[1]);

            int n;
            byte buf[] = new byte[size];

            while (size > 0) {
                n = is.read(buf);
                size -= n;
                os.write(buf, 0, n);
            }

            return os.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ConnectedState.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean isConnected() {
        return !socket.isClosed() && socket.isConnected();
    }

    /**
     * Permet d'obtenir la socket du serveur dans les sous-classes
     *
     * @return La socket de connexion vers le serveur
     */
    protected Socket getSocket() {
        return socket;
    }
}
