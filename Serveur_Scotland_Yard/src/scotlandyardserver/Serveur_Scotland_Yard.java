package scotlandyardserver;

import scotlandyardserver.admin.map.GUIMap;

public class Serveur_Scotland_Yard {

    private static final int DEFAULT_PORT = 3000; // Numéro du port par défaut

    /**
     * Programme principal du server
     *
     * @param args Arguments du programme (numéro de port possible)
     */
    public static void main(String[] args) {
        int usedPort = (args.length > 0) ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        
        new GUIMap(Server.getInstance(usedPort));
    }
}
