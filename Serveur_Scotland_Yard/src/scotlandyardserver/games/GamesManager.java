/**
 * Cette classe permet de gérer les parties
 */

package scotlandyardserver.games;

import java.util.LinkedList;
import scotlandyardserver.client.Client;

public class GamesManager {
    
    private static int NUMBER_MAX_GAMES = 100; // Nombre de parties maximum
    
    private final LinkedList<Game> games = new LinkedList<Game>();
    
    public boolean createGame(Client host, String name, int numberOfPlayers, String map) {

        if(games.size() + 1 > NUMBER_MAX_GAMES)
            return false;
        
        // On regarde qu'il y a pas deja un jeu avec ce nom
        for(Game g : games)
            if(g.getName().equals(name))
                return false;
        
        games.add(new Game(host, name, numberOfPlayers, map));
        return true;
    }
}
