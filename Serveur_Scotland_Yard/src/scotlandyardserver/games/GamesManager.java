/**
 * Cette classe représente le manager des parties
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.games;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.state.InitializingGameState;

public class GamesManager {

    private static final int NUMBER_MAX_GAMES = 100; // Nombre de parties maximum

    private final LinkedList<Game> games = new LinkedList<Game>();

    /**
     * Permet de créer une partie sur le serveur
     *
     * @param host Le client hôte de la partie (celui qui l'a créée)
     * @param name Le nom de la partie
     * @param numberOfPlayers Le nombre de joueurs dans la partie
     * @param map Le nom de la carte de la partie
     * @return Retourne true si la partie a bien été crée, false sinon
     */
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

            if (!rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }

        games.add(new Game(host, name, numberOfPlayers, map));
        return true;
    }

    /**
     * Permet d'avoir la liste des parties qui sont sur le serveur
     *
     * @return La liste des parties crées sur le serveur
     */
    public LinkedList<Game> games() {
        return games;
    }

    /**
     * Permet de terminer une partie
     *
     * @param game La partie a terminer
     */
    public void finishGame(Game game) {
        games.remove(game);
    }

    /**
     * Permet de démarrer une partie
     *
     * @param name Le nom de la partie a démarrer
     */
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
