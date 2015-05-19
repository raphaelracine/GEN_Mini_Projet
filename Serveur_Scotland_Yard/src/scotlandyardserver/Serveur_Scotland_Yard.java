package scotlandyardserver;

import scotlandyardserver.admin.map.GUIMap;
import scotlandyardserver.admin.user.GUIUser;

public class Serveur_Scotland_Yard {

    private static final int DEFAULT_PORT = 3000; // Numéro du port par défaut

    /**
     * Programme principal du server
     *
     * @param args Arguments du programme (numéro de port possible)
     */
    public static void main(String[] args) {
        int usedPort = (args.length > 0) ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        Server server = Server.getInstance(usedPort);        
        new GUIMap(server);
        new GUIUser(server);        
    }
}