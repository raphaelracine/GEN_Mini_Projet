/**
 * Classe qui permet de recevoir la liste des joueurs d'une partie en JSon
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.json;

import java.util.ArrayList;

public class PlayerList {

    private final ArrayList<String> players = new ArrayList<String>();

    public ArrayList<String> players() {
        return players;
    }
}
