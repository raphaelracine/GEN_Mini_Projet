
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

