package scotlandyardserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.admin.map.GUIMap;
import scotlandyardserver.admin.user.GUIUser;
import scotlandyardserver.json.GameMap;
import scotlandyardserver.json.Station;
import javafx.util.Pair;
import scotlandyardserver.json.Link;
import com.google.gson.*;

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
