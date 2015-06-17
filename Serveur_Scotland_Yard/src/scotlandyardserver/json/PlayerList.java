/**
 * Cette classe permet de transmettre la liste des joueurs d'une partie en JSon
 * 
 * Il est à noter que toutes ces méthodes sont décrites dans la classe Game mais
 * elle sont appelées par délégation
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.json;

import java.util.ArrayList;

public class PlayerList {

    private final ArrayList<String> players = new ArrayList<String>();

    public ArrayList<String> players() {
        return players;
    }

    public void add(String player) {
        players.add(player);
    }
}
