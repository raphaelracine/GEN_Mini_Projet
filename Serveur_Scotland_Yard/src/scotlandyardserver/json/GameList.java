/**
 * Cette classe permet de transmettre une liste de partie en JSon
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
import scotlandyardserver.json.Game;

public class GameList {

    private final ArrayList<Game> games = new ArrayList<Game>();

    public void add(Game game) {
        games.add(game);
    }

    public ArrayList<Game> games() {
        return games;
    }
}
