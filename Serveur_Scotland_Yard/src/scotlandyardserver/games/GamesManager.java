/**
 * Cette classe permet de gérer les parties
 */
package scotlandyardserver.games;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.state.InitializingGameState;

public class GamesManager {

    private static int NUMBER_MAX_GAMES = 100; // Nombre de parties maximum

    private final LinkedList<Game> games = new LinkedList<Game>();

    public boolean createGame(Client host, String name, int numberOfPlayers, String map) {

        if (games.size() + 1 > NUMBER_MAX_GAMES) {
            return false;
        }

        // On regarde qu'il y a pas deja un jeu avec ce nom
        for (Game g : games) {
            if (g.getName().equals(name)) {
                return false;
            }
        }
        
        try {
            // On regarde que la carte existe dans la base de données
            ResultSet rs = host.server().getSQLSelection(
                    "SELECT name FROM map WHERE name='" + map + "'");
            
            if(!rs.next())
                return false;
        } catch (SQLException ex) {
            return false;
        }       

        games.add(new Game(host, name, numberOfPlayers, map));
        return true;
    }

    public LinkedList<Game> games() {
        return games;
    }

    public void finishGame(Game game) {
        games.remove(game);
    }

    public void startGame(String name) {
        for (Game game : games) {
            if (game.getName().equals(name)) {
                for (Client c : game.players()) {
                    c.sendMessage("GAMESTART");
                }
                game.setState(new InitializingGameState(game));
                return;
            }
        }
    }
}
