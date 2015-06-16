/**
 * Classe qui permet de recevoir la liste des parties en JSon
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.json;

import java.util.ArrayList;

public class GameList {

    private final ArrayList<Game> games = new ArrayList<Game>();

    public ArrayList<Game> games() {
        return games;
    }
}
